package pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import java.util.AbstractMap.SimpleEntry;

import org.newdawn.slick.util.Log;

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
	
	
	public ArrayList<Node> computePath(Vector2i start, Vector2i end, int mapType) {
		
		/*
		 * Liste ouverte : liste des noeuds étudiés
		 * Liste fermée : liste des noeuds solutions
		 * 
		 * -> Chaque point étudié est mis dans la liste ouverte et le meilleur de cette liste
		 * passe dans la liste fermée, il va servir de base pour la recherche suivante
		 * 
		 * -> A chq itération il ne faut pas uniquement chercher celui qui a la meilleure qualité dans ses voisins, 
		 * mais également dans toute la liste ouverte.
		 * 
		 * -> A chq itération, on va regarder parmi tous les noeuds qui ont été étudiés (et qui n'ont pas encore été choisis) 
		 * celui qui a la meilleure qualité.
		 * 
		 * -> L'algorithme s'arrête quand la destination a été atteinte ou bien lorsque toutes les solutions mises de côté ont été
		 * étudiées et qu'aucune ne s'est révélée bonne, c'est le cas où il n'y a pas de solution.
		 * 
		 * -> avant de se lancer dans l'étude de la qualité de chacun des noeuds adjacents, il ne faut prendre que ceux qui sont vraiment utilisables. 
		 * Il faut aussi mettre de côté tous les noeuds déjà présents dans la liste ouverte ou dans la liste fermée. 
		 * Et pour être plus précis, je dirais qu'il ne faut pas prendre un point s'il est déjà dans la liste ouverte, à moins qu'il ne soit meilleur, 
		 * auquel cas on va mettre à jour la liste ouverte.
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
		
		if (!refMap.hasPath(start.x, start.y, end.x, end.y, mapType)) {
			return null;
		}
		
		SimpleEntry<Integer, Integer> currentPosition = new SimpleEntry<Integer, Integer>(startPoint.x, startPoint.y);
		Node startNode = new Node();
		startNode.parent = new SimpleEntry<Integer, Integer>(0, 0);
		
		openedList.put(currentPosition, startNode);
		addToClosedList(currentPosition);
		storeAdjacentNodes(currentPosition, mapType);
		
		// tant qu'on est pas à la fin du trajet voulu ET que la liste des potentiels sommets n'est pas vide
		while (currentPosition.getKey() != endPoint.x && currentPosition.getValue() != endPoint.y && !openedList.isEmpty()) {
			currentPosition = findBestNodeOfList(openedList);
			addToClosedList(currentPosition);
			storeAdjacentNodes(currentPosition, mapType);
			
			if (currentPosition.getKey() == endPoint.x && currentPosition.getValue() == endPoint.y) {
				System.out.println("Il semblerait que la fin ait été atteinte :)");
			}
		}
		
		// tmp
		return new ArrayList<Node>();
	}
	
	/*
	 * Cherche les voisins et les stocke selon leur valeurs respectives
	 * @param Node currentPosition
	 * @param int mapType
	 */
	private void storeAdjacentNodes(SimpleEntry<Integer, Integer> currentPosition, int mapType) {
		
		SimpleEntry<Integer, Integer> tmpEntry = null;
		Node tmpNode = null;
		
		boolean isInMap = false;
		boolean isCurrentNode = false;
		
		for (int i = currentPosition.getKey() - 1 ; i < currentPosition.getKey() + 1 ; ++i) {
			for (int j = currentPosition.getValue() - 1 ; j < currentPosition.getValue() + 1 ; ++j) {
				
				isInMap = i >= 0 && i < refMap.getWidth() && j >= 0 && j < refMap.getHeight();
				isCurrentNode = i == currentPosition.getKey() && j == currentPosition.getValue();
				
				// case testée existante et != noeud du centre
				if (!isInMap && !isCurrentNode) {
					
					if (!refMap.isTileBlocked(currentPosition.getKey(), currentPosition.getValue(), mapType)) {
						tmpEntry = new SimpleEntry<Integer, Integer>(currentPosition.getKey(), currentPosition.getValue());
						
						if (!closedList.containsKey(tmpEntry)) {
							
							tmpNode = new Node();
							tmpNode.costFromStartToNode = closedList.get(currentPosition).costFromStartToNode 
									+ computeNodeDistance(currentPosition.getKey(), currentPosition.getValue(), i, j);
							tmpNode.costFromNodeToEnd = computeNodeDistance(currentPosition.getKey(), currentPosition.getValue(), endPoint.x, endPoint.y);
							tmpNode.costSum = tmpNode.costFromStartToNode + tmpNode.costFromNodeToEnd;
							
							// si liste ouverte contient déjà le noeud
							if (openedList.containsKey(tmpEntry)) {
								// si le nouveau chemin est plus avantageux que l'ancien, on le remplace
								if (tmpNode.costSum < openedList.get(tmpEntry).costSum) {
									openedList.put(tmpEntry, tmpNode);
								}
								
								// sinon on stocke le noeud comme étant déjà étudié
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
	 * Permet de déterminer quel noeud est le plus performant dans une liste
	 * @param listToSearchIn
	 * @return
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
		Node tmpNode = openedList.get(position);
		closedList.put(position, tmpNode);
		
		if (openedList.remove(position) == null) {
			Log.warn("[A* algorithm] Unable to remove an old position (opened to closed list)");
		}
	}
}
