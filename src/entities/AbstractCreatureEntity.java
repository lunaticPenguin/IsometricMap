package entities;

public abstract class AbstractCreatureEntity extends AbstractEntity implements IMoveable, IAggressive {
	/**
	 * Points d'attaque de l'entité
	 */
	protected int attackPoints;
	
	/**
	 * Si l'entité se déplace
	 */
	protected boolean isMoving;
	
	/**
	 * Vitesse de déplacement
	 */
	protected float speedMove;
	
	/**
	 * Si l'entité se déplace
	 */
	public boolean isMoving() {
		return isMoving;
	}
	
	public void setIsMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	public void update(int delta) {
		if (isMoving) {
			switch (direction) {
			case 0: // north
				s.y -= Math.ceil(speedMove * 2);
				break;
			case 1: // northeast
				s.x += Math.ceil(speedMove * 4);
				s.y -= Math.ceil(speedMove * 2);
			break;
			case 2: // east
				s.x += Math.ceil(speedMove * 4);
			break;
			case 3: // southeast
				s.x += Math.ceil(speedMove * 4);
				s.y += Math.ceil(speedMove * 2);
			break;
			case 4: // south
				s.y += Math.ceil(speedMove * 2);
			break;
			case 5: // southwest
				s.x -= Math.ceil(speedMove * 4);
				s.y += Math.ceil(speedMove * 2);
			break;
			case 6: // west
				s.x -= Math.ceil(speedMove * 4);
			break;
			case 7: // northwest
				s.x -= Math.ceil(speedMove * 4);
				s.y -= Math.ceil(speedMove * 2);
			break;
			}
		}
	}
}
