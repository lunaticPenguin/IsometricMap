package pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import java.util.AbstractMap.SimpleEntry;

import tools.Vector2i;

import map.Map;

public class PathFinder {
	
	protected HashMap<SimpleEntry<Integer, Integer>, Node> openedList;
	protected HashMap<SimpleEntry<Integer, Integer>, Node> closedList;
	
	protected Vector2i startPoint;
	protected Vector2i endPoint;
	
	protected Map refMap;
	
	public PathFinder(Map refMap) {
		this.refMap = refMap;
		
		openedList = new HashMap<SimpleEntry<Integer, Integer>, Node>();
		closedList = new HashMap<SimpleEntry<Integer, Integer>, Node>();
	}
	
	
	public ArrayList<Vector2i> computePath(Vector2i start, Vector2i end, int mapType) {
		
		/*
		 * Liste ouverte : liste des noeuds étudiés
		 * Liste fermée : liste des noeuds solutions
		 * 
		 * ----------------------------
		 * 
		 * Pr chq noeud :
		 * -> Est-ce un obstacle ? si oui, on oublie ce noeud.
		 * 
		 * -> Est-il dans la liste fermée ? si oui, ce noeud a déjà été étudié ou bien est en cours d'étude, on ne fait rien.
		 * 
		 * -> Est-il dans la liste ouverte ? si oui, on calcule la qualité de ce noeud, et si elle est meilleure que celle de son
		 * homologue dans la liste ouverte, on modifie le noeud présent dans la liste ouverte.
		 * 
		 * -> Sinon, on l'ajoute dans la liste ouverte et on calcule sa qualité.
		 * 
		 */
		
		startPoint = new Vector2i(start);
		endPoint = new Vector2i(end);
		
		if (!refMap.hasPath(startPoint, endPoint, mapType)) {
			return null;
		}
		
		SimpleEntry<Integer, Integer> currentPosition = new SimpleEntry<Integer, Integer>(startPoint.x, startPoint.y);
		Node startNode = new Node();
		startNode.parent = new SimpleEntry<Integer, Integer>(0, 0);
		
		openedList.put(currentPosition, startNode);
		addToClosedList(currentPosition);
		storeAdjacentNodes(currentPosition, mapType);
		
		// tant qu'on est pas à la fin du trajet voulu ET que la liste des potentiels sommets n'est pas vide
		while (!(currentPosition.getKey() == endPoint.x && currentPosition.getValue() == endPoint.y) && !openedList.isEmpty()) {
			currentPosition = findBestNodeOfList(openedList);
			addToClosedList(currentPosition);
			storeAdjacentNodes(currentPosition, mapType);
			
			if (currentPosition.getKey() == endPoint.x && currentPosition.getValue() == endPoint.y) {
				
				ArrayList<Vector2i> path = getPath();
				clearList();
				return path;
			}
		}
		
		clearList();
		return null;
	}
	
	/*
	 * Cherche les voisins et les stocke selon leur valeurs respectives
	 * @param Node currentPosition
	 * @param int mapType
	 */
	private void storeAdjacentNodes(SimpleEntry<Integer, Integer> currentPosition, int mapType) {
		
		SimpleEntry<Integer, Integer> tmpEntry = null;
		Node tmpNode = null;
		
		boolean isCurrentNode = false;
		boolean isCellValid = false;
		
		for (int i = currentPosition.getKey() - 1 ; i <= currentPosition.getKey() + 1 ; ++i) {
			for (int j = currentPosition.getValue() - 1 ; j <= currentPosition.getValue() + 1 ; ++j) {
				
				isCurrentNode = (i == currentPosition.getKey() && j == currentPosition.getValue());
				isCellValid = (i >= 0 && i < refMap.getWidth()) && (j >= 0 && j < refMap.getHeight());
				
				// case testée existante et != noeud du centre
				if (isCellValid && !isCurrentNode) {
					
					if (!refMap.isTileBlocked(i, j, mapType)) {
						
						tmpEntry = new SimpleEntry<Integer, Integer>(i, j);
						
						if (!closedList.containsKey(tmpEntry)) {
							
							tmpNode = new Node();
							tmpNode.costFromStartToNode = closedList.get(currentPosition).costFromStartToNode 
									+ computeNodeDistance(i, j, currentPosition.getKey(), currentPosition.getValue());
							
							tmpNode.costFromNodeToEnd = computeNodeDistance(i, j, endPoint.x, endPoint.y);
							
							tmpNode.costSum = tmpNode.costFromStartToNode + tmpNode.costFromNodeToEnd;
							tmpNode.parent = currentPosition;
							
							// si liste ouverte contient déjà le noeud
							if (openedList.containsKey(tmpEntry)) {
								// si le nouveau chemin est plus avantageux que l'ancien, on le remplace
								if (tmpNode.costSum < openedList.get(tmpEntry).costSum) {
									openedList.put(tmpEntry, tmpNode);
								}
								
								// sinon on stocke le noeud comme étant étudié
							} else {
								openedList.put(tmpEntry, tmpNode);
							}
						}
					}
				}
			}
		}
	}
	
	/*
	 * Permet de calculer la distance entre 2 points
	 * 
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 * @return
	 */
	private int computeNodeDistance(int xStart, int yStart, int xEnd, int yEnd) {
		return (int) Math.sqrt(Math.pow((double) xStart - xEnd, 2.0) + Math.pow((double) yStart - yEnd, 2.0));
	}
	
	/*
	 * Permet de déterminer quel noeud est le plus porche dans une liste spécifiée
	 * @param HashMap<SimpleEntry<Integer, Integer>, Node> listToSearchIn
	 * @return SimpleEntry<Integer, Integer>
	 */
	private SimpleEntry<Integer, Integer> findBestNodeOfList(HashMap<SimpleEntry<Integer, Integer>, Node> listToSearchIn) {
		
		int maxCost = 1000;
		Entry<SimpleEntry<Integer, Integer>, Node> tmpEntry;
		SimpleEntry<Integer, Integer> tmpPosition = null;
		
		Set<Entry<SimpleEntry<Integer, Integer>, Node>> tmpSet = listToSearchIn.entrySet();
		
		Iterator<Entry<SimpleEntry<Integer, Integer>, Node>> it = tmpSet.iterator();
		
		while (it.hasNext()) {
			tmpEntry = it.next();
			if (tmpEntry.getValue().costSum < maxCost) {
				maxCost = tmpEntry.getValue().costSum;
				tmpPosition = tmpEntry.getKey();
			}
		}
		
		return tmpPosition;
	}
	
	/*
	 * Permet de passer une position de la liste ouverte à la liste fermée
	 * @param SimpleEntry<Integer, Integer> position clé du noeud à passer
	 */
	private void addToClosedList(SimpleEntry<Integer, Integer> position) {
		closedList.put(position, openedList.get(position));
		openedList.remove(position);
	}
	
	/*
	 * Permet d'obtenir le chemin trouvé lors de l'appel à la méthode computePath
	 * @return ArrayList<Vector2i>
	 */
	private ArrayList<Vector2i> getPath() {
		
		ArrayList<Vector2i> path = new ArrayList<Vector2i>();
		path.add(new Vector2i(endPoint.x, endPoint.y));
		
		SimpleEntry<Integer, Integer> entryEnd = new SimpleEntry<Integer, Integer>(endPoint.x, endPoint.y);
		Node tmpNode = closedList.get(entryEnd);
		SimpleEntry<Integer, Integer> entryParent = tmpNode.parent;
		
		while (entryParent.getKey() != 0 && entryParent.getValue() != 0) {
			path.add(new Vector2i(entryParent.getKey(), entryParent.getValue()));
			entryParent = tmpNode.parent;
			tmpNode = closedList.get(tmpNode.parent);
		}
		
		return path;
	}
	
	private void clearList() {
		openedList.clear();
		closedList.clear();
	}
}
