/**
 * Classe gérant l'aspect principal du jeu, c'est à dire :
 * 	-> gameplay
 *	-> interactions entre les entités
 *	-> interactions serveur/client
 * @author Corentin Legros
 */

package states;

import effects.EffectManager;

import entities.factory.EntityFactory;
import entities.manager.EntityManager;
import entities.manager.ProjectileManager;

import entities.types.buildings.AbstractBuildingEntity;
import entities.types.creatures.AbstractCreatureEntity;

import main.MyGame;
import map.Camera;
import map.Map;
import map.MousePosition;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.SoundManager;

//import de.matthiasmann.twl.Button;

import tools.Position;
import tools.Randomizer;
import tools.Vector2i;


public class MainGameState extends BasicGameState {//BasicTWLGameState { //

	private final int NB_JIDIOKA_TEST = 5;
	
	private Input input;
	
	private Map map;
	private Camera cam;
	private MousePosition mPos;
	
	private boolean isDebugging;
	
	private EntityManager objEntityManager;
	private ProjectileManager objProjectileManager;
	
	private int delta;
	
	private int indextower = 0;
	private int maxIndextower = 0;
	
	private StateBasedGame refGame;

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
		
		refGame = game;
		
		isDebugging = false;
		input = container.getInput();
		input.enableKeyRepeat();
		
		map = new Map("data/maps/island_01.tmx");
		
		cam = Camera.getInstance();
		cam.init(MyGame.X_WINDOW, MyGame.Y_WINDOW, map);
		Vector2i originPos = Position.memoryToScreen(
				cam,
				-Integer.parseInt(map.getMapProperty("focusOriginTileX", "0")),
				-Integer.parseInt(map.getMapProperty("focusOriginTileY", "0"))
		);
		
		
		cam.focusOn(originPos);
		
		mPos = new MousePosition(cam);
		
		objEntityManager = EntityManager.getInstance();
		objProjectileManager = ProjectileManager.getInstance();
		AbstractCreatureEntity tmpEntity;
		
		// positions temporaires
		int x, y;
		for (int i = 0 ; i < NB_JIDIOKA_TEST ; ++i) {
			tmpEntity = (AbstractCreatureEntity) objEntityManager.addEntity(EntityFactory.ENTITY_CREATUREJIDIAKO);
			x = 0;
			y = 0;
			while (map.isTileBlocked(x, y, Map.TILE_GROUND)) {
				x = Randomizer.getInstance().generateRangedInt(0, Map.mDim.x - 1);
				y = Randomizer.getInstance().generateRangedInt(0, Map.mDim.y - 1);
			}
			tmpEntity.setM(new Vector2i(x, y));
			tmpEntity.setIsDiplayed(true);
		}
//		
//		AbstractBuildingEntity tower = (AbstractBuildingEntity) objEntityManager.addEntity(EntityFactory.ENTITY_TOWERGUARD);
//		tower.setM(new Vector2i(17, 20));
//		tower.setIsDiplayed(true);
//		
//		tmpEntity = (AbstractCreatureEntity) objEntityManager.addEntity(EntityFactory.ENTITY_CREATUREJIDIAKO);
//		tmpEntity.setM(new Vector2i(17, 24));
//		tmpEntity.setIsDiplayed(true);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		map.dynamicRender(g);

		objEntityManager.update(container, game, delta, g);
		objProjectileManager.update(container, game, delta, g);
		EffectManager.getInstance().render(g);

		renderMouseTile(g);
		
		if (isDebugging) {
			displayPositions(g);
		}
		g.drawLine(0 + cam.x, 0 + cam.y, 0 + cam.x, 0 + cam.y);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		mainShortcuts();
		cameraMoves();
		mPos.setS(input.getMouseX(), input.getMouseY());
		mPos.m.checkRanges();
		
		// pour optimiser le rendu des entités et des projectiles,
		// on effectue le render en même temps que le update,
		// mais via la méthode MainGameState.render()
		this.delta = delta; 
		
		maxIndextower = indextower > maxIndextower ? indextower : maxIndextower;
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
			refGame.enterState(MyGameState.STATE_MAIN_MENU);
		} else if (input.isKeyPressed(Input.KEY_F1)) {
			isDebugging = !isDebugging;
		}
		
		// testalakon
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
//			Vector2i destPosition = Position.screenToMemory(cam, mPos.s.x, mPos.s.y);
//			Path tmpPath = null;
			
//				tmpPath = map.findPath(bonomesTest[i].getM(), destPosition, Map.TILE_GROUND);
//				if (tmpPath != null) {
//					bonomesTest[i].setCurrentPath(tmpPath);
//				}
		} else if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)) {
			Vector2i destPosition = Position.screenToMemory(cam, mPos.s.x, mPos.s.y);
			
			if (map.isTileBlocked(destPosition.x, destPosition.y)) {
				return;
			}
			
			AbstractBuildingEntity tower = (AbstractBuildingEntity) objEntityManager.addEntity(EntityFactory.ENTITY_TOWERGUARD);
			tower.setM(destPosition);
			tower.setIsDiplayed(true);
			SoundManager.getInstance().get("building_add_1.wav").play();
			
			map.setTileBlocked(destPosition.x, destPosition.y, Map.TILE_GROUND);
			
			++indextower;
		}
	}
	
	protected void renderMouseTile(Graphics g) {
		
		// on récupère les positions idéales de la souris depuis ses coordonnées isométriques
		Vector2i mousePosP = Position.screenToMemory(cam, mPos.s.x, mPos.s.y);
		Position.memoryToScreen(cam, mousePosP, mPos.m.x, mPos.m.y);
		
		if (!map.isTileBlocked(mPos.m.x, mPos.m.y)) {
			g.drawLine(mousePosP.x, mousePosP.y - Map.mTDim.y, mousePosP.x + Map.mTDim.x, mousePosP.y);
			g.drawLine(mousePosP.x + Map.mTDim.x, mousePosP.y, mousePosP.x, mousePosP.y + Map.mTDim.y);
			g.drawLine(mousePosP.x, mousePosP.y + Map.mTDim.y, mousePosP.x - Map.mTDim.x, mousePosP.y);
			g.drawLine(mousePosP.x - Map.mTDim.x, mousePosP.y, mousePosP.x, mousePosP.y - Map.mTDim.y);
		} else {
			g.drawLine(mousePosP.x, mousePosP.y - Map.mTDim.y, mousePosP.x + Map.mTDim.x, mousePosP.y);
			g.drawLine(mousePosP.x + Map.mTDim.x, mousePosP.y, mousePosP.x, mousePosP.y + Map.mTDim.y);
			g.drawLine(mousePosP.x, mousePosP.y + Map.mTDim.y, mousePosP.x - Map.mTDim.x, mousePosP.y);
			g.drawLine(mousePosP.x - Map.mTDim.x, mousePosP.y, mousePosP.x, mousePosP.y - Map.mTDim.y);
			
			g.drawLine(mousePosP.x, mousePosP.y - Map.mTDim.y, mousePosP.x, mousePosP.y + Map.mTDim.y);
			g.drawLine(mousePosP.x + Map.mTDim.x, mousePosP.y, mousePosP.x - Map.mTDim.x, mousePosP.y);
		}
	}
	
	protected void displayPositions(Graphics g) {
		g.drawString("cam [" + cam.x + ";" + cam.y + "]", 10, 25);
		g.drawString("mouse screen[" + mPos.s.x + ";" + mPos.s.y + "] | tile[" + mPos.m.x + ";" + mPos.m.y + "]", 10, 40);
	}
}
