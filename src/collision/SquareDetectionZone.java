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

		g.drawLine(offset.x + s.x, offset.y + s.y, offset.x + s.x + zoneDim.x, offset.y + s.y);
		g.drawLine(offset.x + s.x + zoneDim.x, offset.y + s.y, offset.x + s.x + zoneDim.x, offset.y + s.y + zoneDim.y);
		g.drawLine(offset.x + s.x + zoneDim.x, offset.y + s.y + zoneDim.y, offset.x + s.x, offset.y + s.y + zoneDim.y);
		g.drawLine(offset.x + s.x, offset.y + s.y + zoneDim.y, offset.x + s.x, offset.y + s.y);
	}
}
