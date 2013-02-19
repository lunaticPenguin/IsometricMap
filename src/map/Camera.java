package map;

import tools.Vector2i;

public class Camera extends Vector2i {
	
	public static int UP = 0;
	public static int DOWN = 1;
	public static int LEFT = 2;
	public static int RIGHT = 3;
	
	private static int MOVE_SPEED = 20;
	
	/**
	 * Map (to test available moves)
	 */
	protected Map map;
	
	/**
	 * Displayed zone dimensions
	 */
	protected Vector2i zDim;
	
	/**
	 * Constructeur de caméra.
	 * La caméra est située au centre de la zone dans laquelle elle est spécifiée
	 * 
	 * @param width largeur de la zone dans laquelle la caméra est active (<=> largeur de la fenêtre)
	 * @param height hauteur de la zone dans laquelle la caméra est active (<=> hauteur de la fenêtre)
	 * @param Map map La map, afin d'obtenir ses caractéristiques au niveau dimensions
	 */
	public Camera(int width, int height, Map map) {
		this.map = map;
		zDim = new Vector2i(width, height);
	}
	
	public void moveTo(int direction, float factor) {
		int move = (int) (Camera.MOVE_SPEED * factor);
		int margin = 40;
		
		switch (direction) {
			case 0: // up
				if (y < 0 + margin) {
					y = y + move;
				}
				break;
			case 1: // down
				if (y < margin + map.sDim.y - zDim.y) {
					y = y - move;
				}
				break;
			case 2: // left
				if (x < map.sDim.x / 2 + margin) {
					x = x + move;
				}
				break;
			case 3:// right
				if (x > -(map.sDim.x / 2 + margin + map.tDim.x) + zDim.x) {
					x = x - move;
				}
				break;
		}
	}

	/**
	 * Allow you to focus on a specific zone
	 * @param x
	 * @param y
	 */
	public void focusOn(Vector2i zone) {
		focusOn(zone.x, zone.y);
	}
	
	
	/**
	 * Allow you to focus on a specific zone
	 * @param x
	 * @param y
	 */
	public void focusOn(int x, int y) {
		this.x = x + zDim.x/2;
		this.y = y + zDim.y/2;
	}
}