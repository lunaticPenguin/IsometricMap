package entities.types.projectiles;

import java.util.Iterator;
import java.util.Set;

import entities.AbstractEntity;

import resources.SpriteSheetManager;


public class ProjectileCannonBall extends AbstractProjectile {

	@Override
	public void init(AbstractEntity sender, AbstractEntity receiver) {
		super.init(sender, receiver);
		
		speedMove = 0.02f; // to debug, sinon 0.5f;
		
		SpriteSheetManager objSpritesheetManager = SpriteSheetManager.getInstance();
		
//		Set<String> bla = objSpritesheetManager.getAllKeys();
//		Iterator<String> iterator = bla.iterator();
//		while (iterator.hasNext()) {
//			System.out.println(iterator.next());
//		}
		
		sprite = objSpritesheetManager.get("projectile.cannonball.default");
	}

	@Override
	public int compareTo(AbstractProjectile o) {
		return 0;
	}
}
