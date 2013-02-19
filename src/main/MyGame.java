/**
 * Classe jouant le rôle d'intermédiaire entre le container et le main
 * 
 * @author Corentin Legros
 */


package main;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import states.MyGameState;

public class MyGame {
	
	public static int X_WINDOW = 800;
	public static int Y_WINDOW = 600;
	
	
	ControllerContainer controller;
	String strNameGame;
	
	public MyGame(String strNameGame) {
		this.strNameGame = strNameGame;
	}
	
	public int init() {
		
		try {
			// création du premier état du jeu. Cet état ajoute les autres états possibles du jeu
			controller = new ControllerContainer(new MyGameState(strNameGame));
			controller.setDisplayMode(X_WINDOW, Y_WINDOW, false);
			controller.setTargetFrameRate(60);
			controller.setShowFPS(true);
			
			controller.start();
			
		} catch (SlickException e) {
			Log.error(e);
		}
		return 0;
	}
}
