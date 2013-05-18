package pathfinding;

import collision.SquareDetectionZone;

import tools.Position;
import tools.Vector2i;

public class PathNode extends SquareDetectionZone {
	
	public static final int PIXEL_POSITION = 0;
	public static final int ORTHOGONAL_POSITION = 1;
	
	private Vector2i m;
	
	/**
	 * Noeud précèdent dans le path
	 */
	private PathNode previousNode;
	
	/**
	 * Noeud suivant dans le path
	 */
	private PathNode nextNode;
	
	public PathNode(Vector2i pos, int posType) {
		super();
		switch (posType) {
		case PIXEL_POSITION:
			s = pos;
			m = new Vector2i();
			computeM();
			break;
			
		case ORTHOGONAL_POSITION:
			m = pos;
			s = new Vector2i();
			computeS();
			break;
		}
		previousNode = null;
		nextNode = null;
		zoneDim.x = 50;
		zoneDim.y = 50;
	}
	
	/**
	 * @return the s
	 */
	public Vector2i getS() {
		return s;
	}
	/**
	 * @param s the s to set
	 */
	public void setS(Vector2i s) {
		this.s = s;
		computeM();
	}
	/**
	 * @return the m
	 */
	public Vector2i getM() {
		return m;
	}
	/**
	 * @param m the m to set
	 */
	public void setM(Vector2i m) {
		this.m = m;
		computeS();
	}
	
	/**
	 * Convert iso position to pixels position
	 * /!\ Modify directly internal attributes according to a tile dimensions
	 */
	protected void computeS() {
		s = Position.memoryToScreen(null, m.x, m.y);
	}
	
	/**
	 * Convert pixel position to isometric position
	 * /!\ Modify directly internal attributes according to a tile dimensions.
	 */
	protected void computeM() {
		m = Position.screenToMemory(null, s.x, s.y);
	}

	/**
	 * @return the previousNode
	 */
	public PathNode getPreviousNode() {
		return previousNode;
	}

	/**
	 * @param previousNode the previousNode to set
	 */
	public void setPreviousNode(PathNode previousNode) {
		this.previousNode = previousNode;
	}

	/**
	 * @return the nextNode
	 */
	public PathNode getNextNode() {
		return nextNode;
	}

	/**
	 * @param nextNode the nextNode to set
	 */
	public void setNextNode(PathNode nextNode) {
		this.nextNode = nextNode;
	}
}
