package entities.factory;

import java.util.ArrayList;
import java.util.HashMap;

import entities.types.projectiles.AbstractProjectile;
import entities.types.projectiles.ProjectileCannonBall;

/**
 * Se charge de gérer les unités bâtiments
 * @author co
 *
 */
public class ProjectileFactory extends AbstractFactory<AbstractProjectile> {
	
	// Creatures types
	public static final String PROJECTILE_CANNONBALL = "cannonball";
	
	
	protected static ProjectileFactory instance;
	
	public static ProjectileFactory getInstance() {
		if (instance == null) {
			instance = new ProjectileFactory();
		}
		return instance;
	}
	
	protected ProjectileFactory() {
		dataPool = new HashMap<String, ArrayList<AbstractProjectile>>();
		dataPool.put(PROJECTILE_CANNONBALL, new ArrayList<AbstractProjectile>());
	}
	
	protected AbstractProjectile getSpecificEntity(String entityType) {
		switch (entityType) {
		
		case PROJECTILE_CANNONBALL:
			return new ProjectileCannonBall();
		}
		
		return null;
	}
}
