package entities.types.buildings;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import resources.AnimationManager;
import tools.Vector2i;

import entities.AbstractBuildingEntity;
import entities.AbstractEntity;
import entities.IAggressive;

public class TowerGuard extends AbstractBuildingEntity implements IAggressive {
	
	/**
	 * Tableau d'animation en corrélation directe avec
	 * l'attribut state
	 * 
	 * @see state
	 */
	protected static HashMap<String, ArrayList<Animation>> animationsStates;
	
	public TowerGuard() {
		super();
		
		s = new Vector2f();
		m = new Vector2i();
		
		displayingOffset.x = -32;
		displayingOffset.y = -97;
		
		zoneDim.x = 64;
		zoneDim.y = 112;
		zoneOffset.x = 20;
		zoneOffset.y = 28;
		
		init();
	}
	
	@Override
	public void init() {
		attack = 10;
		defend = 15;
		actionRange = 5;
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean update(GameContainer container, StateBasedGame game, int delta) {
		return false;
	}

	@Override
	public void attack(AbstractEntity entityToAttack) {
		
	}
}
