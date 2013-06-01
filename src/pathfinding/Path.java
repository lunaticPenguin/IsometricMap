package pathfinding;

import java.util.HashSet;
import java.util.LinkedList;

import tools.Vector2i;

/**
 * Cette classe représente un chemin à suivre par une entité.
 * Il est déterminé et conçu par un objet PathFinder.
 * 
 * @author Corentin Legros
 */
public class Path extends LinkedList<PathNode> {
	
	private static final long serialVersionUID = 1L;
	
	protected int cost;
	protected HashSet<Vector2i> checkDoubleNodes;
	
	public Path() {
		checkDoubleNodes = new HashSet<Vector2i>();
	}
	
	/**
	 * Permet d'ajouter un noeud tout en gérant un coût sur l'ensemble du chemin
	 * @param node
	 * @param cost
	 */
	public void addFirst(PathNode node, int cost) {
		
		if (!checkDoubleNodes.contains(node.getM())) {
			
			if (isEmpty()) {
				node.setNextNode(null);
			} else {
				PathNode tmpNode = getFirst();
				tmpNode.setPreviousNode(node);
				node.setNextNode(tmpNode);
			}
			
			this.cost += cost;
			addFirst(node);
			checkDoubleNodes.add(node.getM());
		}
	}
}
