package states;

import main.MyGame;

import org.lwjgl.openal.AL;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.MusicManager;

public class MenuState extends BasicGameState implements ComponentListener {
	
	private StateBasedGame refGame;
	
	/* les deux entrées du menu */
	private MouseOverArea buttonPlay;
	private MouseOverArea buttonQuit;
	
	@Override
	public int getID() {
		return 5;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		refGame = game;
		
		buttonPlay = new MouseOverArea(container,new Image("data/gui/menu_play.png"), (MyGame.X_WINDOW - 134) / 2, 100, this);
		buttonPlay.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		buttonPlay.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));
		
		buttonQuit = new MouseOverArea(container,new Image("data/gui/menu_quit.png"), (MyGame.X_WINDOW - 115) / 2, 250, this);
		buttonQuit.setNormalColor(new Color(0.7f,0.7f,0.7f,1f));
		buttonQuit.setMouseOverColor(new Color(0.9f,0.9f,0.9f,1f));
		
		MusicManager.getInstance();
		MusicManager.getInstance().playMusic("ambience_menu_1.ogg");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		buttonPlay.render(container, g);
		buttonQuit.render(container, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
	}
	
	@Override
	public void componentActivated(AbstractComponent source) {
		if (source == buttonPlay) {
			refGame.enterState(MyGameState.STATE_MAIN_GAME);
		} else if (source == buttonQuit) {
			AL.destroy();
			System.exit(0);
		}
	}
}
