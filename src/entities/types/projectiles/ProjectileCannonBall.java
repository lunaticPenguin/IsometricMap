package entities.types.projectiles;

import org.newdawn.slick.geom.Vector2f;

import resources.SpriteSheetManager;


public class ProjectileCannonBall extends AbstractProjectile {

	public ProjectileCannonBall(Vector2f screenPositionStart,
			Vector2f screenPositionStop) {
		super(screenPositionStart, screenPositionStop);
	}

	@Override
	protected void init() {
		
		speedMove = 0.02f; // to debug, sinon 0.5f;
		
		SpriteSheetManager objSpritesheetManager = SpriteSheetManager.getInstance();
		sprite = objSpritesheetManager.get("projectile.cannonball.default");
	}
}
