package entities;

import map.Camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import pathfinding.Path;
import pathfinding.PathNode;

public abstract class AbstractCreatureEntity extends AbstractEntity implements IMoveable, IAggressive {
	/**
	 * Points d'attaque de l'entité
	 */
	protected int attackPoints;
	
	/**
	 * Si l'entité se déplace
	 */
	protected boolean isMoving;
	
	/**
	 * Vitesse de déplacement
	 */
	protected float speedMove;
	

	protected int nextSX;
	protected int nextSY;

	protected int nextMX;
	protected int nextMY;
	
	protected Path currentPath;
	protected PathNode currentNode;
	
	/**
	 * Si l'entité se déplace
	 */
	public boolean isMoving() {
		return isMoving;
	}
	
	public void setIsMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		if (isMoving) {
			determineDirection();
			switch (direction) {
			case 0: // north
				s.y -= Math.ceil(delta * speedMove * 1);
				break;
			case 1: // northeast
				s.x += Math.ceil(delta * speedMove * 2);
				s.y -= Math.ceil(delta * speedMove * 1);
			break;
			case 2: // east
				s.x += Math.ceil(delta * speedMove * 2);
			break;
			case 3: // southeast
				s.x += Math.ceil(delta * speedMove * 2);
				s.y += Math.ceil(delta * speedMove * 1);
			break;
			case 4: // south
				s.y += Math.ceil(delta * speedMove * 1);
			break;
			case 5: // southwest
				s.x -= Math.ceil(delta * speedMove * 2);
				s.y += Math.ceil(delta * speedMove * 1);
			break;
			case 6: // west
				s.x -= Math.ceil(delta * speedMove * 2);
			break;
			case 7: // northwest
				s.x -= Math.ceil(delta * speedMove * 2);
				s.y -= Math.ceil(delta * speedMove * 1);
			break;
			}
		}
	}
	
	/**
	 * Permet d'afficher l'entité sur une zone de l'écran
	 * @param g
	 * @param cam
	 */
	public void draw(Graphics g, Camera cam) {
		if (isMoving) {
			this.getCurrentAnimation().draw(cam.x + s.x, cam.y + s.y);
		} else {
			this.getCurrentAnimation().getImage(0).draw(cam.x + s.x, cam.y + s.y);
		}
	}
	
	/**
	 * Détermine la direction utilisée par l'entité
	 * 
	 * TODO : ajouter une marge "d'erreur" proportionnelle à sa distance
	 * (trouver un calcul performant) permettant de ne pas osciller
	 * entité trop lointaine du point de passage
	 */
	private void determineDirection() {
		
		if (s.x < nextSX) {
			// vers l'est
			if (s.y < nextSY) {
				// vers le haut (nord-est)
			}
			if (s.y > nextSY) {
				// vers le bas (sud-est)
			}
		}
		
		if (s.x > nextSX) {
			// vers l'ouest
			if (s.y < nextSY) {
				// vers le haut (nord-ouest)
			}
			if (s.y > nextSY) {
				// vers le bas (sud-ouest)
			}
		}
	}
}
