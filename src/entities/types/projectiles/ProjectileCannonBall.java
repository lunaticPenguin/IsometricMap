package entities.types.projectiles;

import entities.AbstractEntity;

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
	}

	@Override
	public int compareTo(AbstractProjectile o) {
		return 0;
	}
}
