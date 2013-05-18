package collision;

import org.newdawn.slick.Graphics;

import tools.Vector2i;

public abstract class SquareDetectionZone extends AbstractZoneDetection {

	
	/**
	 * Permet de savoir si l'élèment en cours est en collision avec une zone
	 * rectangulaire.
	 * 
	 * @param SquareDetectionZone otherZone
	 * @return boolean
	 */
	public boolean isColliding(SquareDetectionZone otherZone) {
		return (s.x > otherZone.getS().x && s.x < otherZone.getS().x + otherZone.getZoneDim().x)
				&& (s.y > otherZone.getS().y && s.y < otherZone.getS().y + otherZone.getZoneDim().y)
				|| 
				(otherZone.getS().x > s.x && otherZone.getS().x < s.x + zoneDim.x)
				&& (otherZone.getS().y > s.y && otherZone.getS().y < s.y + zoneDim.y)
				;
	}
	
	/**
	 * Permet de savoir si l'élèment en cours est en collision avec un autre élèment
	 * @param DetectionZone otherZone
	 * @return boolean
	 */
	public boolean isColliding(OvalDetectionZone otherZone) {
		return (s.x > otherZone.getS().x && s.x < otherZone.getS().x + otherZone.getZoneDim().x)
				&& (s.y > otherZone.getS().y && s.y < otherZone.getS().y + otherZone.getZoneDim().y);
	}
	
	/**
	 * Méthode de détecter la collision entre une rectangle et un point
	 * 
	 * @param zone
	 * @return boolean
	 */
	public boolean isColliding(Vector2i point) {
		return (point.x > s.x && point.x < s.x + zoneDim.x)
				&& (point.y > s.y && point.y < s.y + zoneDim.y);
	}
	
	/**
	 * Permet d'afficher la zone de collision d'une entité rectangulaire
	 * sujette aux collisions
	 * 
	 * @param Graphics g
	 * @param Vector2i offset
	 */
	public void renderCollidingZone(Graphics g, Vector2i offset) {
		if (offset == null) {
			offset = new Vector2i();
		}
		

		Vector2i a = new Vector2i(displayingOffset.x + offset.x + zoneOffset.x + s.x,
				displayingOffset.y + offset.y + zoneOffset.y + s.y);
		Vector2i b = new Vector2i(displayingOffset.x + offset.x + zoneOffset.x + s.x + zoneDim.x,
				displayingOffset.y + offset.y + zoneOffset.y + s.y);
		Vector2i c = new Vector2i(displayingOffset.x + offset.x + zoneOffset.x + s.x + zoneDim.x,
				displayingOffset.y + offset.y + zoneOffset.y + s.y + zoneDim.y);
		Vector2i d = new Vector2i(displayingOffset.x + offset.x + zoneOffset.x + s.x,
				displayingOffset.y + offset.y + zoneOffset.y + s.y + zoneDim.y);

		g.drawLine( a.x, a.y, b.x, b.y);
		g.drawLine( b.x, b.y, c.x, c.y);
		g.drawLine( c.x, c.y, d.x, d.y);
		g.drawLine( d.x, d.y, a.x, a.y);
	}
}
