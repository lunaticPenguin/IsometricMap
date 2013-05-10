/**
 * Classe d'initialisation des différents états du jeu.
 * Cette classe permet également de passer d'un état à un autre
 * 
 * @author Corentin Legros
 */


package states;

import java.net.URL;

//import gui.TWLInputAdapter;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

//import twlslick.TWLStateBasedGame;

public class MyGameState extends StateBasedGame {//TWLStateBasedGame { //

	public final static int STATE_MAIN_MENU = 5;
	public final static int STATE_MAIN_GAME = 10;
	
	
	public MyGameState(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new MainGameState());
		

		container.getGraphics().setBackground(new Color(51, 59, 230));
	}

	protected URL getThemeURL() {
		return null;
	}
	
}
