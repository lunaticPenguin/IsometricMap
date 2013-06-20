package entities;

import map.Camera;
import map.Map;

import org.newdawn.slick.Graphics;

public abstract class AbstractBuildingEntity extends AbstractEntity implements IStationary {
	
	/**
	 * Permet de surcharger l'affiche d'une entité (par exemple à des fins de debug)
	 * @param g
	 */
	public void draw(Graphics g) {
		Camera cam = Camera.getInstance();
		g.drawOval(cam.x + s.x - (actionRange * Map.tDim.x), cam.y + s.y - (actionRange * Map.tDim.y), actionRange * Map.tDim.x * 2, actionRange * Map.tDim.y * 2);
		super.draw(g);
	}
}
