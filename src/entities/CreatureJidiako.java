package entities;

import tools.Vector2i;

/**
 * Classe représentant le premier ennemi du jeu (moment clé dans l'histoire du jeu)
 * @author Corentin Legros
 */
public class CreatureJidiako extends AbstractCreatureEntity {

	@Override
	public void moveTo(Vector2i matrixCell) {
		
	}

	@Override
	public void attack(AbstractCreatureEntity entityToAttack) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		this.life = 100;
		s.x = 0;
		s.y = 0;
		
		isDisplayed = false;
		
	}

	@Override
	public void doAction() {
		
	}
	
}
