package collision;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.Log;

import tools.Vector2i;

public abstract class AbstractZoneDetection {
	
	/**
	 * Offset pour gérer le décalage dû au sprites.
	 * Afin d'éviter de redéfinir le décalage dans d'autre classe c'est celui-ci
	 * qui doit être utilisé
	 */
	protected Vector2i displayingOffset;
	
	/**
	 * Offset pour spécifier la zone précise que l'on souhaite détecter
	 */
	protected Vector2i zoneOffset;
	
	/**
	 * Pixel position on screen
	 */
	protected Vector2i s;
	
	/**
	 * Rendered square dimension
	 */
	protected Vector2i zoneDim;
	
	
	/***
	 * Initialisations via constructeur
	 */
	public AbstractZoneDetection() {
		displayingOffset = new Vector2i();
		zoneOffset = new Vector2i();
		s = new Vector2i();
		zoneDim = new Vector2i();
	}
	
	/**
	 * @return the zoneOffset
	 */
	public Vector2i getZoneOffset() {
		return zoneOffset;
	}


	/**
	 * @param zoneOffset the zoneOffset to set
	 */
	public void setZoneOffset(Vector2i zoneOffset) {
		this.zoneOffset.x = zoneOffset.x;
		this.zoneOffset.y = zoneOffset.y;
	}

	/**
	 * Specify ZoneOffset with the two params
	 * @param int x
	 * @param int y
	 */
	public void setZoneOffset(int x, int y) {
		this.zoneOffset.x = x;
		this.zoneOffset.y = y;
	}


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
	 * @return the displayingOffset
	 */
	public Vector2i getDisplayingOffset() {
		return displayingOffset;
	}

	/**
	 * @param displayingOffset the displayingOffset to set
	 */
	public void setDisplayingOffset(Vector2i displayingOffset) {
		this.displayingOffset = displayingOffset;
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
	 * Méthode par défaut permettant de savoir si une détection de collision 
	 * utilisant un objet de type AbstractZoneDetection n'a pas été implémentée
	 * 
	 * @param zone
	 * @return boolean
	 */
	public boolean isColliding(AbstractZoneDetection zone) {
		Log.warn("No collision detection using AbstractZoneDetection type implemented");
		return false;
	}
	
	/**
	 * Méthode de détecter la collision entre une forme et un point
	 * 
	 * @param zone
	 * @return boolean
	 */
	public abstract boolean isColliding(Vector2i point);
	
	
	/**
	 * Permet d'afficher la zone de collision d'une entité sujette aux collisions
	 * @param Graphics g
	 * @param Vector2i offset
	 */
	public abstract void renderCollidingZone(Graphics g, Vector2i offset);
}
