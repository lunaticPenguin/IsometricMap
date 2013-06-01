package pathfinding;

import org.newdawn.slick.geom.Vector2f;

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
	
	/**
	 * Pathnode constructor using pixels (screen) position.
	 * @param pos
	 */
	public PathNode(Vector2f pos) {
		super();
		
		s = pos;
		m = new Vector2i();
		computeM();
		
		previousNode = null;
		nextNode = null;
		
		// spécification de la zone de collision
		zoneOffset.x = -32;
		zoneOffset.y = -16;
		zoneDim.x = 64;
		zoneDim.y = 32;
	}
	
	/**
	 * Pathnode constructor using orthogonal position.
	 * @param pos
	 */
	public PathNode(Vector2i pos) {
		super();
		
		m = pos;
		s = new Vector2f();
		computeS();
		
		previousNode = null;
		nextNode = null;
		
		// spécification de la zone de collision
		zoneOffset.x = -32;
		zoneOffset.y = -16;
		zoneDim.x = 64;
		zoneDim.y = 32;
	}
	
	/**
	 * @return the s
	 */
	public Vector2f getS() {
		return s;
	}
	/**
	 * @param s the s to set
	 */
	public void setS(Vector2f s) {
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
		Vector2i tmp = Position.memoryToScreen(null, m.x, m.y);
		s.x = tmp.x;
		s.y = tmp.y;
	}
	
	/**
	 * Convert pixel position to isometric position
	 * /!\ Modify directly internal attributes according to a tile dimensions.
	 */
	protected void computeM() {
		m = Position.screenToMemory(null, (int) s.x, (int) s.y);
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
