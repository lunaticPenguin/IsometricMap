package entities.types.projectiles;

import org.newdawn.slick.geom.Vector2f;

import resources.SpriteSheetManager;


public class ProjectileCannonBall extends AbstractProjectile {

	@Override
	public void init(Vector2f screenPositionStart, Vector2f screenPositionStop) {
		super.init(screenPositionStart, screenPositionStop);
		
		speedMove = 0.02f; // to debug, sinon 0.5f;
		
		SpriteSheetManager objSpritesheetManager = SpriteSheetManager.getInstance();
		sprite = objSpritesheetManager.get("projectile.cannonball.default");
	}

	@Override
	public int compareTo(AbstractProjectile o) {
		return 0;
	}
}
