package entities;

import java.util.ArrayList;
import java.util.HashMap;

import main.MyGame;
import map.Camera;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import tools.Position;
import tools.Vector2i;

public abstract class AbstractEntity {
	
	/**
	 * Les 8 différentes directions
	 * /!\ les directions ne sont pas translatés à l'orientation isométrique
	 * de la map (le Nord est le nord commun (<=> vers le haut))
	 */
	public static final int DIRECTION_NORTH = 0;
	public static final int DIRECTION_NORTHEAST = 1;
	public static final int DIRECTION_EAST = 2;
	public static final int DIRECTION_SOUTHEAST = 3;
	public static final int DIRECTION_SOUTH = 4;
	public static final int DIRECTION_SOUTHWEST = 5;
	public static final int DIRECTION_WEST = 6;
	public static final int DIRECTION_NORTHWEST = 7;
	
	/**
	 * Direction de l'entité
	 */
	protected Integer direction;
	
	/**
	 * Etat de l'entité (pour correspondre directement avec les sprites
	 * et les animations
	 */
	protected String state;
	
	/**
	 * Pixel position on screen
	 */
	protected Vector2i s;
	
	/**
	 * Matrix position in memory
	 */
	protected Vector2i m;
	
	/**
	 * Rendered square dimension
	 */
	protected Vector2i dim;
	
	/**
	 * entity life
	 */
	protected int life;
	
	/**
	 * Si l'entité doit être affichée
	 * à l'écran
	 */
	protected boolean isDisplayed;
	
	/**
	 * Permet de savoir si cette entité est traçable à l'écran.
	 * Comprendre : "Savoir si l'entité a à être tracé à l'écran"
	 * 
	 * /!\ ne prend pas encore en compte la largeur et la hauteur de l'entité
	 * 
	 * @param Camera cam permet de déterminer la zone affichée
	 * @return boolean
	 */
	public boolean belongToRenderedAera(Camera cam) {
		return isDisplayed && ((cam.x + s.x > 0) && (cam.x + s.x < MyGame.X_WINDOW)) && ((cam.y + s.y > 0) && (cam.y + s.y < MyGame.Y_WINDOW));
	}
	
	
	/**
	 * Permet de [ré]initialiser l'entité,
	 * en vue d'une première ou une autre potentielle utilisation
	 * dans le cadre d'une pool (via factory)
	 */
	abstract public void init();
	
	
	/**
	 * Permet à l'entité d'effectuer une action spéciale
	 */
	abstract public void doAction();

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Vector2i getS() {
		return s;
	}

	public void setS(Vector2i s) {
		this.s = s;
	}
	
	public void setS(int x, int y) {
		this.s.x = x;
		this.s.y = y;
	}

	public void setM(Vector2i m) {
		this.m = m;
		s = Position.memoryToScreen(null, m.x, m.y);
	}
	
	public Vector2i getM() {
		return m;
	}

	public Vector2i getDim() {
		return dim;
	}

	public void setDim(Vector2i dim) {
		this.dim = dim;
	}
	
	public void setIsDiplayed(boolean isDisplayed) {
		this.isDisplayed = isDisplayed;
	}
	
	public void updateMatrixPosition(Camera cam) {
		this.m = Position.screenToMemory(cam, s.x, s.y);
	}
	
	/**
	 * Permet d'obtenir la vie de l'entité
	 * @return
	 */
	public int getLife() {
		return life;
	}

	/**
	 * Permet d'ajouter ou retrancher de la vie à l'entité
	 * @param lifepointsToAdd
	 */
	public void addLife(int lifepointsToAdd) {
		this.life += lifepointsToAdd;
	}
	
	/**
	 * Permet de savoir si l'entité peut être considéré comme out
	 * @return boolean
	 */
	public boolean isDead() {
		return this.life <= 0;
	}
	
	
	abstract public HashMap<String, ArrayList<Animation>> getAnimationStates();
	
	
	/**
	 * Permet d'obtenir l'animation en cours pour l'objet courant
	 * @return
	 */
	public Animation getCurrentAnimation() {
		if (this.getAnimationStates().containsKey(this.state)) {
			return this.getAnimationStates().get(this.state).get(this.direction);
		}
		return null;
	}
	
	/**
	 * Permet à une entité de se défendre lorsqu'elle est attaquée.
	 * Concrêtement une entité aggressive fera appel à cette méthode
	 * depuis sa méthode IAgressive.attack().
	 * @param damagePoints
	 * @see IAggressive.attack()
	 */
	abstract public void defend(int damagePoints);
	
	
	/**
	 * 
	 * @param delta
	 */
	abstract public void update(GameContainer container, StateBasedGame game, int delta);
	
	/**
	 * Permet d'afficher l'entité sur une zone de l'écran
	 * @param g
	 * @param cam
	 */
	public void draw(Graphics g, Camera cam) {
		this.getCurrentAnimation().draw(cam.x + s.x, cam.y + s.y);
	}
}
