package gui;

import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import tools.Vector2i;

public class MapScene extends Scene {
	
	public MapScene(Vector2i position, int width, int height) {
		super(position, width, height);
	}


	@Override
	public void init(GameContainer container, StateBasedGame game) {
		addWidget(new ButtonWidget(new Vector2i(200,200), 30, 20, "ok")); // /!\ positionnement relatif
		Widget tmp;
		
		Iterator<Widget> it = widgets.iterator();
		while (it.hasNext()) {
			tmp = it.next();
			input.addListener(tmp);
			tmp.init(container, game);
		}
	}
	
	
	public void render(Graphics g) {
		renderWidgets(g);
		//g.drawString("MAP SCENE RENDER", 250, 250);
	}
	
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		//System.out.println("x["+x+"]; y["+y+"] " + clickCount);
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		
	}

	@Override
	public void mouseWheelMoved(int change) {
		
	}

	@Override
	public void inputEnded() {
		
	}

	@Override
	public void inputStarted() {
		
	}

	@Override
	public boolean isAcceptingInput() {
		return true;
	}

	@Override
	public void setInput(Input input) {
		this.input = input;
	}

	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerButtonPressed(int controller, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerButtonReleased(int controller, int button) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerDownPressed(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerDownReleased(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerLeftPressed(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerLeftReleased(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerRightPressed(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerRightReleased(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerUpPressed(int controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerUpReleased(int controller) {
		// TODO Auto-generated method stub
	}
	
	public Scene getParent() {
		return this;
	}
}
