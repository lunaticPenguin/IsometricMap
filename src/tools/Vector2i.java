package tools;

public class Vector2i {
	public int x;
	public int y;

	private boolean boolHasXRange = false;
	private boolean boolHasYRange = false;
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	
	public Vector2i() {
		x = 0;
		y = 0;
	}
	
	public Vector2i(Vector2i other) {
		x = other.x;
		y = other.y;
	}
	
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return new String("(" + x + ";" + y + ")");
	}
	

	/**
	 * Permet de spécifier un intervalle pour la variable x
	 * @param Vector2i intervalle à spécifier
	 */
	public void setXRange(Vector2i range) {
		boolHasXRange = true;
		minX = range.x;
		maxX = range.y;
	}
	
	/**
	 * Permet de spécifier un intervalle pour la variable y
	 * @param Vector2i intervalle à spécifier
	 */
	public void setYRange(Vector2i range) {
		boolHasYRange = true;
		minY = range.x;
		maxY = range.y;
	}

	/**
	 * Permet de spécifier un intervalle pour la variable x
	 * @param minX borne minimum de x
	 * @param maxX borne maximum de x
	 */
	public void setXRange(int minX, int maxX) {
		boolHasXRange = true;
		this.minX = minX;
		this.maxX = maxX;
	}
	
	/**
	 * Permet de spécifier un intervalle pour la variable y
	 * @param minY
	 * @param maxY
	 */
	public void setYRange(int minY, int maxY) {
		boolHasYRange = true;
		this.minY = minY;
		this.maxY = maxY;
	}
	
	
	/**
	 * Vérifie et applique les intervalles
	 * si définis
	 */
	public void checkRanges() {
		checkXRange();
		checkYRange();
	}
	
	/**
	 * Vérifie la définition d'un intervalle
	 * et l'applique s'il est défini pour la variable x
	 */
	public void checkXRange() {
		if (boolHasXRange) {
			x = (x < minX) ? minX : (x >= maxX) ? maxX - 1 : x;
		}
	}
	

	/**
	 * Vérifie la définition d'un intervalle
	 * et l'applique s'il est défini pour la variable y
	 */
	public void checkYRange() {
		if (boolHasYRange) {
			y = (y < minY) ? minY : (y >= maxY) ? maxY - 1 : y;
		}
	}

	public Vector2i getXRange() {
		return boolHasXRange ? new Vector2i(minX, maxX) : new Vector2i();
	}
	
	public Vector2i getYRange() {
		return boolHasYRange ? new Vector2i(minY, maxY) : new Vector2i();
	}
}
