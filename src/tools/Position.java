package tools;

public class Position {
	
	/**
	 * Converti une position isométrique à une position pixel, avec un offset
	 * 
	 * @param offset Vector2i (nullable)
	 * @param tileX int abcisse de position isométrique
	 * @param tileY int ordonnée de position isométrique
	 * @param mTW int la moitié de la largeur d'une tile
	 * @param mTH int la moitié de la hauteur d'une tile
	 * 
	 * @return Vector2i
	 */
	public static Vector2i memoryToScreen(Vector2i offset, int tileX, int tileY, int mTW, int mTH) {
		return Position.memoryToScreen(offset, new Vector2i(), tileX, tileY, mTW, mTH);
	}

	/**
	 * Converti une position isométrique à une position pixel, avec un offset
	 * Permet de spécifier le vecteur d'entiers pour éviter une création inutile
	 * 
	 * @param offset Vector2i (nullable)
	 * @param pos Vector2i (nullable)
	 * @param tileX int abcisse de position isométrique
	 * @param tileY int ordonnée de position isométrique
	 * @param mTW int moitié de la largeur d'une tile
	 * @param mTH int moitié de la hauteur d'une tile
	 * 
	 * @return Vector2i pos
	 */
	public static Vector2i memoryToScreen(Vector2i offset, Vector2i pos, int tileX, int tileY, int mTW, int mTH) {

		pos.x = (tileX - tileY) * mTW + mTW;
		pos.y = (tileX + tileY) * mTH;
		
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
	 * @param tW int largeur d'une tile
	 * @param tH int hauteur d'une tile
	 * @param width int largeur de la carte totale (en tile)
	 * @param height int hauteur de la carte totale (en tile)
	 * 
	 * @return Vector2i
	 */
	public static Vector2i screenToMemory(Vector2i offset, int x, int y, int tW, int tH, int width, int height) {
		return Position.screenToMemory(offset, new Vector2i(), x, y, tW, tH, width, height);
	}
	
	
	/**
	 * Converti une position pixel en une position isométrique, avec un offset
	 * Permet de spécifier le vecteur d'entiers pour éviter une création inutile
	 * 
	 * @param offset Vector2i (nullable)
	 * @param pos Vector2i positions de retour
	 * @param x int abcisse de position pixel
	 * @param y int ordonnée de position pixel
	 * @param tW int largeur d'une tile
	 * @param tH int hauteur d'une tile
	 * @param width int largeur de la carte totale (en tile)
	 * @param height int hauteur de la carte totale (en tile)
	 * 
	 * @return Vector2i pos
	 */
	public static Vector2i screenToMemory(Vector2i offset, Vector2i pos, int x, int y, int tW, int tH, int width, int height) {
		
		if (offset != null) {
			x -= offset.x;
			y -= offset.y;
		}
		
//		xt=(y*2+x)/w; 
//		yt=(y*2-x)/w;
		
		pos.x = (int) Math.round((y * 2 + x) / tW);
		pos.y = (int) Math.round((y * 2 - x) / tW) + 1;
		
		pos.x = (pos.x < 0) ? 0 : ((pos.x >= width) ? width - 1: pos.x);
		pos.y = (pos.y < 0) ? 0 : ((pos.y >= height) ? height - 1: pos.y);

		return pos;
	}
}
