package entities.types.buildings;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import entities.AbstractBuildingEntity;
import entities.AbstractEntity;
import entities.IAggressive;

public class TowerSimple extends AbstractBuildingEntity implements IAggressive {
	
	/**
	 * Tableau d'animation en corrélation directe avec
	 * l'attribut state
	 * 
	 * @see state
	 */
	protected static HashMap<String, ArrayList<Animation>> animationsStates;
	
	
	@Override
	public void init() {
		attack = 10;
		defend = 15;
	}

	@Override
	public void doAction() {
		
	}

	@Override
	public HashMap<String, ArrayList<Animation>> getAnimationStates() {
		return TowerSimple.animationsStates;
	}

	@Override
	public void defend(int damagePoints) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attack(AbstractEntity entityToAttack) {
		
	}
}
