package collision;

import org.newdawn.slick.util.Log;

import tools.Vector2i;

public abstract class AbstractZoneDetection {
	/**
	 * Pixel position on screen
	 */
	protected Vector2i s;
	
	/**
	 * Rendered square dimension
	 */
	protected Vector2i zoneDim;
	
	/**
	 * Permet d'obtenir la position en pixel de la zone
	 * @return
	 */
	public Vector2i getS() {
		return s;
	}
	
	/**
	 * Permet de définir la position en pixel de la zone
	 * @param Vector2i s
	 */
	public void setS(Vector2i s) {
		this.s = s;
	}
	
	/**
	 * Permet de définir la position en pixel de la zone
	 * @param int x
	 * @param int y
	 */
	public void setS(int x, int y) {
		this.s.x = x;
		this.s.y = y;
	}

	/**
	 * @return the zoneDim
	 */
	public Vector2i getZoneDim() {
		return zoneDim;
	}

	/**
	 * @param zoneDim the zoneDim to set
	 */
	public void setZoneDim(Vector2i zoneDim) {
		this.zoneDim = zoneDim;
	}
	
	/**
	 * Permet de savoir si une collision n'a pas été implémentée
	 * @param zone
	 * @return boolean
	 */
	public boolean isColliding(AbstractZoneDetection zone) {
		Log.warn("No collision type implemented");
		return true;
	}
}
