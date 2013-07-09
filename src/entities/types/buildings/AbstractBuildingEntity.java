package entities.types.buildings;

import map.Camera;
import map.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import entities.AbstractEntity;
import entities.IStationary;

import entities.manager.ProjectileManager;
import entities.types.projectiles.AbstractProjectile;

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
	
	
	@Override
	public boolean update(GameContainer container, StateBasedGame game, int delta) {

		if (target != null && target.isDead()) {
			target = null; // tour disponible
		}
		
		if (target != null && System.currentTimeMillis() > nextTimeForShot) {
			AbstractProjectile tmpProjectile = (AbstractProjectile) ProjectileManager.getInstance().addEntity(weaponType);
			tmpProjectile.init(this, target);
			nextTimeForShot = System.currentTimeMillis() + reloadDuration;
		}
		return false;
	}
}
