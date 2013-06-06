package entities;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class TowerSimple extends AbstractBuildingEntity {

	@Override
	public void init() {
		attack = 10;
		defend = 15;
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap<String, ArrayList<Animation>> getAnimationStates() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void defend(int damagePoints) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// TODO Auto-generated method stub
		
	}
	
}
