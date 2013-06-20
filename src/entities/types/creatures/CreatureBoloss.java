package entities.types.creatures;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import entities.AbstractCreatureEntity;
import entities.AbstractEntity;
import entities.IMoveable;

import resources.AnimationManager;
import tools.Vector2i;

/**
 * Classe représentant le premier ennemi du jeu (moment clé dans l'histoire du jeu)
 * @author Corentin Legros
 */
public class CreatureBoloss extends AbstractCreatureEntity {
	
	/**
	 * Tableau d'animation en corrélation directe avec
	 * l'attribut state
	 * 
	 * @see state
	 */
	protected static HashMap<String, ArrayList<Animation>> animationsStates;
	
	
	public CreatureBoloss() {
		super();
		
		s = new Vector2f();
		m = new Vector2i();
		
		displayingOffset.x = -23;
		displayingOffset.y = -40;
		
		zoneDim.x = 27;
		zoneDim.y = 45;
		zoneOffset.x = 12;
		zoneOffset.y = 0;
		
		init();
	}
	

	@Override
	public HashMap<String, ArrayList<Animation>> getAnimationStates() {
		return CreatureBoloss.animationsStates;
	}
	
	public void defend(int damagePoints) {
		life -= damagePoints;
	}
	
	@Override
	public void moveTo(Vector2i matrixCell) {
		
	}

	@Override
	public void attack(AbstractEntity entityToAttack) {
		
	}

	public void init() {
		
		life = 500;
		attack = 3;
		defend = 1;
		s.x = 0;
		s.y = 0;
		
		speedMove = 0.05f;
		
		isDisplayed = false;
		isMoving = false;
		
		this.direction = DIRECTION_SOUTHEAST;
		this.state = STATE_WALKING;
		
		AnimationManager objAnimationManager = AnimationManager.getInstance();
		
		if (animationsStates == null) {
			animationsStates = new HashMap<String, ArrayList<Animation>>();
			String tmpKey = new String("creature.boloss.walk");
			StringBuilder tmpSBKey = new StringBuilder();
			ArrayList<Animation> stateAnimations = new ArrayList<Animation>();
			
			for (int i = 0 ; i < 8 ; ++i) {
				tmpSBKey.append(tmpKey).append("_").append(i);
				stateAnimations.add(objAnimationManager.get(tmpSBKey.toString()));
				tmpSBKey.setLength(0);
			}
			animationsStates.put(IMoveable.STATE_WALKING, stateAnimations);
		}
	}

	@Override
	public void doAction() {
		
	}
	
	@Override
	public boolean update(GameContainer container, StateBasedGame game, int delta) {
		return super.update(container, game, delta);
	}
}
