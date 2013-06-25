package entities;

import java.util.ArrayList;
import java.util.HashMap;

import map.Camera;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import collision.SquareDetectionZone;

import tools.Position;
import tools.Vector2i;

public abstract class AbstractEntity extends SquareDetectionZone 
implements Comparable<AbstractEntity> {
	
	
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
	 * Matrix position in memory
	 */
	protected Vector2i m;
	
	/**
	 * entity life
	 */
	protected int life;

	/**
	 * entity attack points
	 */
	protected float attack;
	
	/**
	 * entity attack points
	 */
	protected float defend;
	
	/**
	 * Si l'entité doit être affichée
	 * à l'écran
	 */
	protected boolean isDisplayed;
	
	/**
	 * Rayon d'action de l'entité
	 */
	protected int actionRange;
	
	/**
	 * Durée de rechargement
	 */
	protected int reloadDuration;
	
	/**
	 * Temps pour lequel l'unité pour à nouveau tirer
	 */
	protected long nextTimeForShot;
	
	/**
	 * Origine du point de tir (par rapport à la position s)
	 */
	protected Vector2i firePoint;
	
	protected boolean highlight = false;
	
	protected AbstractEntity target;
	
	protected String weaponType;
	
	public void setHighLight(boolean highlight) {
		this.highlight = highlight;
	}
	
	/**
	 * Permet de savoir si cette entité est traçable à l'écran.
	 * Comprendre : "Savoir si l'entité a à être tracé à l'écran"
	 * 
	 * /!\ ne prend pas encore en compte la largeur et la hauteur de l'entité
	 * 
	 * @return boolean
	 */
	public boolean belongToRenderedAera() {
		return isDisplayed && super.belongToRenderedAera();
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
	
	public void setM(Vector2i m) {
		this.m = m;
		Vector2i tmp = Position.memoryToScreen(null, m.x, m.y);
		s.x = tmp.x;
		s.y = tmp.y;
	}
	
	public Vector2i getM() {
		return m;
	}
	
	public void setIsDiplayed(boolean isDisplayed) {
		this.isDisplayed = isDisplayed;
	}
	
	public void updateMatrixPosition(Camera cam) {
		this.m = Position.screenToMemory(cam, (int) s.x, (int) s.y);
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
	public void defend(int damagePoints) {
		this.life -= damagePoints;
	}
	
	
	/**
	 * 
	 * @param delta
	 */
	abstract public boolean update(GameContainer container, StateBasedGame game, int delta);
	
	/**
	 * Permet d'afficher l'entité sur une zone de l'écran
	 * @param g
	 */
	public void draw(Graphics g) {
		Camera cam = Camera.getInstance();
		this.getCurrentAnimation().draw(cam.x + s.x + displayingOffset.x, cam.y + s.y + displayingOffset.y);
		
		if (highlight) {
			renderCollidingZone(g);
		}
	}
	
	/**
	 * Permet de comparer les entités selon leur positions Y (screen).
	 * 
	 * @return int (-1|0|1)
	 * 
	 * {@inheritDoc}
	 */
	public int compareTo(AbstractEntity other) {
		if (s.y < other.getS().y) {
			return -1;
		} else if (s.y == other.getS().y) {
			return 0;
		} else {
			return 1;
		}
	}
	
	/**
	 * Permet d'obtenir le rayon d'action d'une entité
	 * @return int
	 */
	public int getActionRange() {
		return actionRange;
	}
	
	/**
	 * Pour savoir si 2 entités ennemies doivent interagir (proximité)
	 * 
	 * (utilise le concept d'intercection des cercles 
	 * 
	 * @param entity
	 * @return
	 */
	public boolean isEntityInActionZone(AbstractEntity entity) {
		
		return (m.x - entity.getM().x) * (m.x - entity.getM().x)
				+ (m.y - entity.getM().y) * (m.y - entity.getM().y)
			< (actionRange + entity.getActionRange()) * (actionRange + entity.getActionRange());
	}
	
	/**
	 * Permet de spécifier un objet cible si aucune n'a déjà été attribuée.
	 * 
	 * @param AbstractEntity entity
	 * @see setTarget(AbstractEntity) pour forcer la cible
	 */
	public void assignTarget(AbstractEntity entity) {
		if (target == null) {
			setTarget(entity);
		}
	}
	
	
	/**
	 * Permet de spécifier un objet "cible", c-a-d un objet sur lequel il 
	 * faut effectuer une action
	 * @param AbstractEntity entity
	 */
	public void setTarget(AbstractEntity entity) {
		target = entity;
	}
	
	/**
	 * Permet de récupérer un objet "cible"
	 * @return AbstractEntity target
	 */
	public AbstractEntity getTarget() {
		return target;
	}
	
	/**
	 * Permet d'avoir les coordonnées du point
	 */
	public Vector2f getFirePoint() {
		return new Vector2f(s.x + firePoint.x, s.y + firePoint.y);
	}
}
