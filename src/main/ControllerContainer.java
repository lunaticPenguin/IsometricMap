/**
 * Classe permettant d'ajouter des comportements de haut niveau au container du jeu,
 * d'où la notion de contrôleur.
 * Celle-ci joue en effet le rôle de fournisseur de services pour les classes d'états
 * (configuration, entités (fabriques), network..etc)
 */

package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class ControllerContainer extends AppGameContainer {
	
	/**
	 * Le premier état du jeu permet en 
	 * effet d'initialiser les autres états du jeu
	 */
	StateBasedGame objStateInitializer;
	
	public ControllerContainer (StateBasedGame g) throws SlickException {
		super(g);
		objStateInitializer = g;
	}
	
	/**
	 * Permet d'obtenir l'état courant du jeu
	 * @return GameState
	 */
	public GameState getCurrentState() {
		return objStateInitializer.getCurrentState();
	}
	
	/**
	 * Permet d'obtenir l'identifiant de l'état courant du jeu
	 * @return int
	 */
	public int getCurrentStateID() {
		return objStateInitializer.getCurrentStateID();
	}
	
	
}
