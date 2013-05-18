package collision;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.Log;

import tools.Vector2i;

public abstract class AbstractZoneDetection {
	
	public AbstractZoneDetection() {
		s = new Vector2i();
		zoneDim = new Vector2i();
	}
	
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
	 * Méthode par défaut permettant de savoir si une collision n'a pas été implémentée
	 * @param zone
	 * @return boolean
	 */
	public boolean isColliding(AbstractZoneDetection zone) {
		Log.warn("No collision type implemented");
		return true;
	}
	
	
	/**
	 * Permet d'afficher la zone de collision d'une entité sujette aux collisions
	 * @param Graphics g
	 * @param Vector2i offset
	 */
	public abstract void renderCollidingZone(Graphics g, Vector2i offset);
}
