package collision;

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
}
