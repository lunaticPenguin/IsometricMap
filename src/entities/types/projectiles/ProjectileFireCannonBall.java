package entities.types.projectiles;

import entities.AbstractEntity;

import resources.SpriteSheetManager;

public class ProjectileFireCannonBall extends AbstractProjectile {
	
	@Override
	public void init(AbstractEntity sender, AbstractEntity receiver) {
		
		super.init(sender, receiver);
		speedMove = 1f;
		displayingOffset.x = -10;
		displayingOffset.y = -10;
		zoneDim.x = 10;
		zoneDim.y = 10;
		
		SpriteSheetManager objSpritesheetManager = SpriteSheetManager.getInstance();
		sprite = objSpritesheetManager.get("projectile.firecannonball.default");
	}

	@Override
	public int compareTo(AbstractProjectile o) {
		return 0;
	}
}
