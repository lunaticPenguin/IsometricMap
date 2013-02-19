package map;

import tools.Vector2i;
import tools.Position;

/**
 * Classe représentant la position de la souris afin de gérer simplement les conversions de position orthogonale et isométrique
 * @author eleve
 */
public class MousePosition {
	
	/**
	 * Pixel position on screen
	 */
	public Vector2i s;
	
	/**
	 * Matrix position in memory
	 */
	public Vector2i m;
	
	protected Vector2i tileDim;
	protected Vector2i tileMiDim;
	protected Vector2i mapDim;
	
	protected Camera cam;
	
	/**
	 * Créé un gestionnaire de position de la souris
	 * @param cam caméra pour l'offset
	 * @param tileWidth
	 * @param tileHeight
	 * @param mapWidth
	 * @param mapHeight
	 */
	public MousePosition(Camera cam, int tileWidth, int tileHeight, int mapWidth, int mapHeight) {
		s = new Vector2i();
		m = new Vector2i();
		
		tileDim = new Vector2i(tileWidth, tileHeight);
		tileMiDim = new Vector2i(tileWidth/2, tileHeight/2);
		mapDim = new Vector2i(mapWidth, mapHeight);

		m.setXRange(0, mapDim.x);
		m.setYRange(0, mapDim.y);
		
		this.cam = cam;
	}

	public void setM(int x, int y) {
		m.x = x + cam.x;
		m.y = y + cam.y;
		computeS();
	}

	public void setM(Vector2i pos) {
		m.x = pos.x + cam.x;
		m.y = pos.y + cam.y;
		computeS();
	}
	
	public void setMX(int x) {
		m.x = x;
		computeS();
	}
	
	public void setMY(int y) {
		m.y = y;
		computeS();
	}

	public void setS(int x, int y) {
		s.x = x;
		s.y = y;
		computeM();
	}
	
	public void setS(Vector2i pos) {
		s.x = pos.x;
		s.y = pos.y;
		computeM();
	}
	
	public void setSX(int x) {
		s.x = x;
		computeM();
	}
	
	public void setSY(int y) {
		s.y = y;
		computeM();
	}
	
	/**
	 * Convert iso position to pixels position
	 * /!\ Modify directly internal attributes according to a tile dimensions
	 */
	protected void computeS() {
		Position.memoryToScreen(cam, s, m.x, m.y, tileMiDim.x, tileMiDim.y);
	}
	
	/**
	 * Convert pixel position to isometric position
	 * /!\ Modify directly internal attributes according to a tile dimensions.
	 */
	protected void computeM() {
		Position.screenToMemory(cam, m, s.x, s.y, tileDim.x, tileDim.y, mapDim.x, mapDim.y);
	}
}
