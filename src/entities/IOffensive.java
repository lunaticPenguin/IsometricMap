package entities;

/**
 * Permet de donner un comportement aggressif à des entités.
 * @author Corentin Legros
 */
public interface IOffensive {
	
	/**
	 * Aggressive entity should be able to attack all other entities
	 */
	public void attack(AbstractEntity entityToAttack);
}
