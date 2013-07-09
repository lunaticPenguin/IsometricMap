package effects;

import org.newdawn.slick.geom.Vector2f;

import resources.AnimationManager;

/**
 * Effet d'une explosion de bombe
 */
public class EffectNuke extends AbstractEffect {
	
	@Override
	protected void param() {
		animation = AnimationManager.getInstance().get("explosion.nuke_0");
		duration = 576;
		displayingOffset.x = -56;
		displayingOffset.y = -100;
	}
	
	@Override
	public void init(Vector2f position) {
		s = position;
	}
}
