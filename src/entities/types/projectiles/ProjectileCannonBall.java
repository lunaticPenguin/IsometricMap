package entities.types.projectiles;

import entities.AbstractEntity;

import resources.SoundManager;
import resources.SpriteSheetManager;


public class ProjectileCannonBall extends AbstractProjectile {

	@Override
	public void init(AbstractEntity sender, AbstractEntity receiver) {
		
		super.init(sender, receiver);
		speedMove = 0.5f;
		displayingOffset.x = -10;
		displayingOffset.y = -10;
		zoneDim.x = 10;
		zoneDim.y = 10;
		
		attack = 20;
		
		SpriteSheetManager objSpritesheetManager = SpriteSheetManager.getInstance();
		sprite = objSpritesheetManager.get("projectile.cannonball.default");

		SoundManager.getInstance().get("cannonball_fire.wav").play();
	}
	
	protected void onCollision() {
		SoundManager.getInstance().get("cannonball_impact.wav").play();
	}

	@Override
	public int compareTo(AbstractProjectile o) {
		return 0;
	}
}
