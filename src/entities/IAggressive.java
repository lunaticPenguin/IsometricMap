package entities;

/**
 * Permet de donner un comportement aggressif à des entités.
 * @author Corentin Legros
 */
public interface IAggressive {
	/**
	 * Aggressive entity should be able to attack all other entities
	 * @param AbstractEntity entityToAttack
	 */
	public void attack(AbstractEntity entityToAttack);
}
