package pathfinding;

import map.Map;
import tools.Position;
import tools.Vector2i;

public class PathNode {
	
	public static final int PIXEL_POSITION = 0;
	public static final int ORTHOGONAL_POSITION = 1;
	
	private Vector2i s;
	private Vector2i m;
	
	public PathNode(Vector2i pos, int posType) {
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
		s = Position.memoryToScreen(null, s, m.x, m.y, Map.mTDim.x, Map.mTDim.y);
	}
	
	/**
	 * Convert pixel position to isometric position
	 * /!\ Modify directly internal attributes according to a tile dimensions.
	 */
	protected void computeM() {
		m = Position.screenToMemory(null, m, s.x, s.y, Map.tDim.x, Map.tDim.y, Map.mDim.x, Map.mDim.y);
	}
}
