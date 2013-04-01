package entities;

import tools.Vector2i;

public interface IMoveable {
	
	public final String STATE_WALKING = "walking";
	public final String STATE_RUNNING = "running";
	
	/**
	 * Permet d'indiquer à une entité où elle doit se déplacer (d'un point de vue de la matrice orthogonale)
	 * @param matrixCell
	 */
	public void moveTo(Vector2i matrixCell);
	
	/**
	 * Si l'entité se déplace
	 * @return
	 */
	public boolean isMoving();
}
