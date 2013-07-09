package entities.factory;

import java.util.ArrayList;
import java.util.HashMap;

import entities.types.projectiles.AbstractProjectile;
import entities.types.projectiles.ProjectileCannonBall;
import entities.types.projectiles.ProjectileFireCannonBall;

/**
 * Se charge de gérer les unités bâtiments
 * @author co
 *
 */
public class ProjectileFactory extends AbstractFactory<AbstractProjectile> {
	
	// Creatures types
	public static final String PROJECTILE_CANNONBALL = "ProjectileCannonBall";
	public static final String PROJECTILE_FIRECANNONBALL = "ProjectileFireCannonBall";
	
	
	
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
		dataPool.put(PROJECTILE_FIRECANNONBALL, new ArrayList<AbstractProjectile>());
	}
	
	protected AbstractProjectile getSpecificEntity(String entityType) {
		switch (entityType) {

		case PROJECTILE_CANNONBALL:
			return new ProjectileCannonBall();
		case PROJECTILE_FIRECANNONBALL:
			return new ProjectileFireCannonBall();
		}
		
		return null;
	}
}
