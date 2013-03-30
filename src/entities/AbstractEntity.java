package entities;

import java.util.ArrayList;

import map.Camera;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

import tools.Vector2i;

public abstract class AbstractEntity {
	
	/**
	 * Les 8 différentes directions
	 */
	public final Integer DIRECTION_NORTH = 0;
	public final Integer DIRECTION_NORTHEAST = 1;
	public final Integer DIRECTION_EAST = 2;
	public final Integer DIRECTION_SOUTHEAST = 3;
	public final Integer DIRECTION_SOUTH = 4;
	public final Integer DIRECTION_SOUTHWEST = 5;
	public final Integer DIRECTION_WEST = 6;
	public final Integer DIRECTION_NORTHWEST = 7;
	
	/**
	 * Direction de l'entité
	 */
	protected Integer direction;
	
	/**
	 * Etat de l'entité (pour correspondre directement avec les sprites
	 * et les animations
	 */
	protected Integer state;
	
	/**
	 * Tableau de spriteSheet
	 * représentant les différents état de l'entité (ressource).
	 * Chaque spritesheet contient les spriteset
	 * correspondants pour les 8 directions
	 * 
	 * @param ArrayList<SpriteSheet>
	 */
	protected ArrayList<SpriteSheet> spritesStates;
	
	/**
	 * Tableau d'animation en corrélation directe avec
	 * l'attribut spritesStates
	 * 
	 * @see spritesStates
	 */
	protected ArrayList<Animation> animationsStates;
	
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
	 * @param Camera cam permet de déterminer la zone affichée
	 * @return boolean
	 */
	public boolean belongToRenderedAera(Camera cam) {
		return isDisplayed && ((cam.x < s.x) && (cam.x + cam.zDim.x > s.x)) && ((cam.y < s.y) && (cam.y + cam.zDim.y > s.y));
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Vector2i getS() {
		return s;
	}

	public void setS(Vector2i s) {
		this.s = s;
	}

	public Vector2i getM() {
		return m;
	}

	public void setM(Vector2i m) {
		this.m = m;
	}

	public Vector2i getDim() {
		return dim;
	}

	public void setDim(Vector2i dim) {
		this.dim = dim;
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
}
