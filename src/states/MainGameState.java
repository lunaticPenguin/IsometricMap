/**
 * Classe gérant l'aspect principal du jeu, c'est à dire :
 * 	-> gameplay
 *	-> interactions entre les entités
 *	-> interactions serveur/client
 * @author Corentin Legros
 */

package states;

import gui.MapScene;
import gui.Scene;

import java.util.ArrayList;
import java.util.Iterator;

import main.MyGame;
import map.Camera;
import map.Map;
import map.MousePosition;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

//import de.matthiasmann.twl.Button;

import tools.Position;
import tools.Vector2i;
import twlslick.BasicTWLGameState;
import twlslick.RootPane;


public class MainGameState extends BasicGameState {//BasicTWLGameState { //

	private Input input;
	
	private Map map;
	private Camera cam;
	private MousePosition mPos;
	
	private boolean isDebugging;
	private ArrayList<Scene> scenes;
	
	
	

    /* TWL TEST */
	
    //private Button btn;

    @Override
//    protected RootPane createRootPane() {
//        RootPane rp = super.createRootPane();
//        
//        btn = new Button("Hello World");
//        btn.addCallback(new Runnable() {
//            public void run() {
//                System.out.println("It works!");
//            }
//        });
//
//        rp.add(btn);
//        return rp;
//    }

//    @Override
//    protected void layoutRootPane() {
//        btn.adjustSize();
//        btn.setPosition(100, 100);
//    }
    
    /* TWL TEST */
	
	
	public int getID() {
		return 10;
	}
	
	
	
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		isDebugging = false;
		input = container.getInput();
		input.enableKeyRepeat();
		
		map = new Map("data/maps/nature.tmx");
		
		
		cam = new Camera(MyGame.X_WINDOW, MyGame.Y_WINDOW, map);
		Vector2i originPos = Position.memoryToScreen(
				cam,
				-Integer.parseInt(map.getMapProperty("focusOriginTileX", "0")),
				-Integer.parseInt(map.getMapProperty("focusOriginTileY", "0")),
				map.mTDim.x,
				map.mTDim.y);
		
		
		cam.focusOn(originPos);
		
		mPos = new MousePosition(cam, map.tDim.x, map.tDim.y, map.mDim.x, map.mDim.y);
		
		// gestion des différentes scenes
		scenes = new ArrayList<Scene>();
		initScenes(container, game);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		map.dynamicRender(cam, g);

		renderMouseTile(g);
		
		if (isDebugging) {
			displayPositions(g);
		}
		
		renderScenes(g);
		
		g.drawLine(0 + cam.x, 0 + cam.y, 0 + cam.x, 0 + cam.y);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		mainShortcuts();
		cameraMoves();
		mPos.setS(input.getMouseX(), input.getMouseY());
		mPos.m.checkRanges();
	}
	
	protected void cameraMoves() {
		
		int marginSize = 40;
		
		if (!isDebugging) {
			if (mPos.s.y > 0 && mPos.s.y < marginSize) {
		        cam.moveTo(Camera.UP, (marginSize - (float) mPos.s.y) / marginSize);
		    }
		
		    if (mPos.s.y > MyGame.Y_WINDOW - marginSize && mPos.s.y < MyGame.Y_WINDOW) {
		        cam.moveTo(Camera.DOWN, ((float) mPos.s.y - (MyGame.Y_WINDOW - marginSize)) / marginSize);
		    }
		
		     
		    if (mPos.s.x > 0 && mPos.s.x < marginSize) {
		        cam.moveTo(Camera.LEFT, (marginSize - (float) mPos.s.x) / marginSize);
		    }
		     
		    if (mPos.s.x > MyGame.X_WINDOW - marginSize && mPos.s.x < MyGame.X_WINDOW) {
		        cam.moveTo(Camera.RIGHT, ((float) mPos.s.x - (MyGame.X_WINDOW - marginSize)) / marginSize);
		    }
		}
		
		if (input.isKeyPressed(Input.KEY_UP)) {
			cam.moveTo(Camera.UP, 1.0f);
		} else if (input.isKeyPressed(Input.KEY_DOWN)) {
			cam.moveTo(Camera.DOWN, 1.0f);
		} else if (input.isKeyPressed(Input.KEY_LEFT)) {
			cam.moveTo(Camera.LEFT, 1.0f);
		} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
			cam.moveTo(Camera.RIGHT, 1.0f);
		}
	}
	
	protected void mainShortcuts() {
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(0);
		} else if (input.isKeyPressed(Input.KEY_F1)) {
			isDebugging = !isDebugging;
		}
	}
	
	
	protected void renderMouseTile(Graphics g) {
		
		// on récupère les positions idéales de la souris depuis ses coordonnées isométriques
		Vector2i mousePosP = Position.screenToMemory(cam, mPos.s.x, mPos.s.y, map.tDim.x, map.tDim.y, map.mDim.x, map.mDim.y);
		Position.memoryToScreen(cam, mousePosP, mPos.m.x, mPos.m.y, map.mTDim.x, map.mTDim.y);
		
		Color c = new Color(255,255,255);
		
		if (!map.isTileBlocked(mPos.m.x, mPos.m.y)) {
			g.drawLine(mousePosP.x, mousePosP.y - map.mTDim.y, mousePosP.x + map.mTDim.x, mousePosP.y);
			g.drawLine(mousePosP.x + map.mTDim.x, mousePosP.y, mousePosP.x, mousePosP.y + map.mTDim.y);
			g.drawLine(mousePosP.x, mousePosP.y + map.mTDim.y, mousePosP.x - map.mTDim.x, mousePosP.y);
			g.drawLine(mousePosP.x - map.mTDim.x, mousePosP.y, mousePosP.x, mousePosP.y - map.mTDim.y);
			
			g.drawString(mPos.m.toString(), mousePosP.x-map.mTDim.x, mousePosP.y-map.mTDim.y);
		} else {
			
			g.drawLine(mousePosP.x, mousePosP.y - map.mTDim.y, mousePosP.x + map.mTDim.x, mousePosP.y);
			g.drawLine(mousePosP.x + map.mTDim.x, mousePosP.y, mousePosP.x, mousePosP.y + map.mTDim.y);
			g.drawLine(mousePosP.x, mousePosP.y + map.mTDim.y, mousePosP.x - map.mTDim.x, mousePosP.y);
			g.drawLine(mousePosP.x - map.mTDim.x, mousePosP.y, mousePosP.x, mousePosP.y - map.mTDim.y);
			
			g.drawLine(mousePosP.x, mousePosP.y - map.mTDim.y, mousePosP.x, mousePosP.y + map.mTDim.y);
			g.drawLine(mousePosP.x + map.mTDim.x, mousePosP.y, mousePosP.x - map.mTDim.x, mousePosP.y);
		}
	}
	
	
	protected void displayPositions(Graphics g) {
		g.drawString("cam [" + cam.x + ";" + cam.y + "]", 10, 25);
		g.drawString("mouse screen[" + mPos.s.x + ";" + mPos.s.y + "] | tile[" + mPos.m.x + ";" + mPos.m.y + "]", 10, 40);
	}
	
	
	protected void initScenes(GameContainer container, StateBasedGame game) {
		scenes.add(new MapScene(new Vector2i(0,0), MyGame.X_WINDOW, MyGame.Y_WINDOW));
		Scene tmp;
		
		Iterator<Scene> it = scenes.iterator();
		while (it.hasNext()) {
			tmp = it.next();
			tmp.setInput(input);
			tmp.init(container, game);
			input.addListener(tmp);
		}
	}
	
	
	protected void renderScenes(Graphics g) {
		Iterator<Scene> it = scenes.iterator();
		while(it.hasNext()) {
			it.next().render(g);
		}
	}
}
