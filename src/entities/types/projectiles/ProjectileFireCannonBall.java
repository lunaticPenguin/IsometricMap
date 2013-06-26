package entities.types.projectiles;

import effects.AbstractEffect;
import effects.EffectManager;
import entities.AbstractEntity;
import entities.factory.EffectFactory;

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
		
		attack = 80;
		
		SpriteSheetManager objSpritesheetManager = SpriteSheetManager.getInstance();
		sprite = objSpritesheetManager.get("projectile.firecannonball.default");
	}

	@Override
	public int compareTo(AbstractProjectile o) {
		return 0;
	}
	
	protected void onCollision() {
		AbstractEffect tmpEffect = EffectManager.getInstance().addEntity(EffectFactory.EFFECT_NUKEBOMB);
		tmpEffect.init(s);
		tmpEffect.start();
	}
}
