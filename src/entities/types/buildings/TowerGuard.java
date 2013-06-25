package entities.types.buildings;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import resources.AnimationManager;
import tools.Vector2i;

public class TowerGuard extends AbstractBuildingEntity {
	
	/**
	 * Tableau d'animation en corrélation directe avec
	 * l'attribut state
	 * 
	 * @see state
	 */
	protected static HashMap<String, ArrayList<Animation>> animationsStates;
	
	public TowerGuard() {
		super();
		
		m = new Vector2i();
		firePoint = new Vector2i(0, -90);
		displayingOffset.x = -34;
		displayingOffset.y = -95;
		
		zoneDim.x = 64;
		zoneDim.y = 112;
		
		init();
	}
	
	@Override
	public void init() {
		weaponType = "firecannonball";
		attack = 10;
		defend = 15;
		actionRange = 5;
		reloadDuration = 1000;
		nextTimeForShot = System.currentTimeMillis() + reloadDuration;
		
		this.direction = DIRECTION_NORTH;
		this.state = STATE_DESTROYED;
		
		AnimationManager objAnimationManager = AnimationManager.getInstance();
		
		if (animationsStates == null) {
			animationsStates = new HashMap<String, ArrayList<Animation>>();
			
			String statesKeys[] = new String[]{"healthy", "damaged", "destroyed"};
			String longStatesKeys[] = new String[]{"tower.guard.healthy", "tower.guard.damaged", "tower.guard.destroyed"};
			StringBuilder tmpSBKey = new StringBuilder();
			
			ArrayList<Animation> stateAnimations = new ArrayList<Animation>();
			
			for (int i = 0 ; i < statesKeys.length ; ++i) {
				tmpSBKey.append(longStatesKeys[i]).append("_").append(0);
				
				stateAnimations.add(objAnimationManager.get(tmpSBKey.toString()));
				tmpSBKey.setLength(0);
				animationsStates.put(statesKeys[i], stateAnimations);
			}
		}
	}
	
	@Override
	public void doAction() {
		
	}

	@Override
	public HashMap<String, ArrayList<Animation>> getAnimationStates() {
		return TowerGuard.animationsStates;
	}

	@Override
	public void defend(int damagePoints) {
		life -= damagePoints;
	}

	@Override
	public boolean update(GameContainer container, StateBasedGame game, int delta) {
		return super.update(container, game, delta);
	}
}
