package tools;

import map.Map;

public class Position {
	
	/**
	 * Converti une position isométrique à une position pixel, avec un offset
	 * 
	 * @param offset Vector2i (nullable)
	 * @param tileX int abcisse de position isométrique
	 * @param tileY int ordonnée de position isométrique
	 * 
	 * @return Vector2i
	 */
	public static Vector2i memoryToScreen(Vector2i offset, int tileX, int tileY) {
		return Position.memoryToScreen(offset, new Vector2i(), tileX, tileY);
	}

	/**
	 * Converti une position isométrique à une position pixel, avec un offset
	 * Permet de spécifier le vecteur d'entiers pour éviter une création inutile
	 * 
	 * @param offset Vector2i (nullable)
	 * @param pos Vector2i (nullable)
	 * @param tileX int abcisse de position isométrique
	 * @param tileY int ordonnée de position isométrique
	 * 
	 * @return Vector2i pos
	 */
	public static Vector2i memoryToScreen(Vector2i offset, Vector2i pos, int tileX, int tileY) {
		
		pos.x = (tileX - tileY) * Map.mTDim.x + Map.mTDim.x;
		pos.y = (tileX + tileY) * Map.mTDim.y;
		
		if (offset != null) {
			pos.x += offset.x;
			pos.y += offset.y;
		}
		return pos;
	}
	
	/**
	 * Converti une position pixel en une position isométrique, avec un offset.
	 * 
	 * @param offset Vector2i (nullable)
	 * @param x int abcisse de position pixel
	 * @param y int ordonnée de position pixel
	 * 
	 * @return Vector2i
	 */
	public static Vector2i screenToMemory(Vector2i offset, int x, int y) {
		return Position.screenToMemory(offset, new Vector2i(), x, y);
	}
	
	
	/**
	 * Converti une position pixel en une position isométrique, avec un offset
	 * Permet de spécifier le vecteur d'entiers pour éviter une création inutile
	 * 
	 * @param offset Vector2i (nullable)
	 * @param pos Vector2i positions de retour
	 * @param x int abcisse de position pixel
	 * @param y int ordonnée de position pixel
	 * 
	 * @return Vector2i pos
	 */
	public static Vector2i screenToMemory(Vector2i offset, Vector2i pos, int x, int y) {
		
		if (offset != null) {
			x -= offset.x;
			y -= offset.y;
		}
		
//		xt=(y*2+x)/w; 
//		yt=(y*2-x)/w;
		
		pos.x = (int) Math.round((y * 2 + x) / Map.tDim.x);
		pos.y = (int) Math.round((y * 2 - x) / Map.tDim.x) + 1;
		
		pos.x = (pos.x < 0) ? 0 : ((pos.x >= Map.mDim.x) ? Map.mDim.x - 1: pos.x);
		pos.y = (pos.y < 0) ? 0 : ((pos.y >= Map.mDim.y) ? Map.mDim.y - 1: pos.y);

		return pos;
	}
}
