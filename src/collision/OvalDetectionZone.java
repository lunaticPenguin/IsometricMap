package collision;

import org.newdawn.slick.Graphics;

import tools.Vector2i;

public class OvalDetectionZone extends AbstractZoneDetection {

	public boolean isColliding(OvalDetectionZone zone) {
		return false;
	}
	
	
	public boolean isColliding(SquareDetectionZone zone) {
		return false;
	}
	
	/**
	 * Permet d'afficher la zone de collision d'une entité ovale sujette aux collisions
	 * @param Graphics g
	 * @param Vector2i offset
	 */
	public void renderCollidingZone(Graphics g, Vector2i offset) {
		if (offset == null) {
			offset = new Vector2i();
		}
		g.drawOval(offset.x + s.x, offset.y + s.y, zoneDim.x, zoneDim.y);
	}
}
