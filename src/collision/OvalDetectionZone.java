package collision;

public class OvalDetectionZone extends AbstractZoneDetection {

	public boolean isColliding(OvalDetectionZone zone) {
		return false;
	}
	
	
	public boolean isColliding(SquareDetectionZone zone) {
		return false;
	}
}
