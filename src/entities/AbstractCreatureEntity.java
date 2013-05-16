package entities;

import map.Camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import pathfinding.Path;
import pathfinding.PathNode;
import tools.Randomizer;

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
		
		if (s.x == nextSX && s.y == nextSY) {
			
			PathNode nextNode = currentNode.getNextNode();
			if (nextNode == null) {
				System.out.println("Arrivé à la fin :)");
				currentPath = null;
				isMoving = false;
			} else {
				setCurrentNode(nextNode);
			}
		}
		
		boolean boolOnLeft = s.x < nextSX;
		boolean boolOnTop = s.y < nextSY;

		boolean boolOnVAxis = s.x > nextSX - 5 && s.x < nextSX + 5;
		boolean boolOnHAxis = s.y > nextSY - 5 && s.y < nextSY + 5;
		String debugDest = "autre cas";
		
		int tmpRatio = 0;
		int divider = 1;
		
		System.out.println(s + " -> (" + nextSX + ";" + nextSY + ") : ");
		
		 	if (boolOnVAxis && boolOnTop) { // middle top
				direction = DIRECTION_SOUTH;
				debugDest = "vers le bas";
			} else if (boolOnVAxis && !boolOnTop) { // middle bottom
				direction = DIRECTION_NORTH;
				debugDest = "vers le haut";
			} else if (boolOnHAxis && boolOnLeft) { // middle left
				direction = DIRECTION_EAST;
				debugDest = "vers la gauche";
			} else if (boolOnHAxis && !boolOnLeft) { // middle right
				direction = DIRECTION_WEST;
				debugDest = "vers la droite";
			}
		 	
			System.out.println(s + " -> (" + nextSX + ";" + nextSY + ") : " + debugDest);
		 	
		 	/* else if (boolOnLeft && boolOnTop) { // top left (OK)
			
			divider = nextSY - s.y;
			divider = divider == 0 ? 1 : divider;
			
			tmpRatio = (nextSX - s.x)/divider;
			if (tmpRatio < 0.25f) {
				direction = DIRECTION_EAST;
			} else if (tmpRatio > 1) {
				direction = DIRECTION_SOUTH;
			} else {
				direction = DIRECTION_SOUTHEAST;
			}
		} else if (boolOnLeft && !boolOnTop) { // bottom left (OK)
			
			divider = s.y - nextSY;
			divider = divider == 0 ? 1 : divider;
			
			tmpRatio = (nextSX - s.x)/divider;
			if (tmpRatio < 0.25f) {
				direction = DIRECTION_EAST;
			} else if (tmpRatio > 1) {
				direction = DIRECTION_NORTH;
			} else {
				direction = DIRECTION_NORTHEAST;
			}
		} else if (!boolOnLeft && boolOnTop) { // top right (OK /!\)
			
			divider = nextSY - s.y;
			divider = divider == 0 ? 1 : divider;
			
			tmpRatio = (s.x - nextSX)/divider;
			if (tmpRatio < 0.25f) {
				direction = DIRECTION_WEST;
			} else if (tmpRatio > 1) {
				direction = DIRECTION_SOUTH;
			} else {
				direction = DIRECTION_SOUTHWEST;
			}
		} else if (!boolOnLeft && !boolOnTop) { // bottom right (OK)
			
			divider = s.y - nextSY;
			divider = divider == 0 ? 1 : divider;
			
			tmpRatio = (s.x - nextSX)/divider;
			if (tmpRatio < 0.25f) {
				direction = DIRECTION_WEST;
			} else if (tmpRatio > 1) {
				direction = DIRECTION_NORTH;
			} else {
				direction = DIRECTION_NORTHWEST; // sinon en diagonale :)
			}
		}*/
	}

	/**
	 * @return the nextSX
	 */
	public int getNextSX() {
		return nextSX;
	}

	/**
	 * @param nextSX the nextSX to set
	 */
	public void setNextSX(int nextSX) {
		this.nextSX = nextSX;
	}

	/**
	 * @return the nextSY
	 */
	public int getNextSY() {
		return nextSY;
	}

	/**
	 * @param nextSY the nextSY to set
	 */
	public void setNextSY(int nextSY) {
		this.nextSY = nextSY;
	}

	/**
	 * @return the nextMX
	 */
	public int getNextMX() {
		return nextMX;
	}

	/**
	 * @param nextMX the nextMX to set
	 */
	public void setNextMX(int nextMX) {
		this.nextMX = nextMX;
	}

	/**
	 * @return the nextMY
	 */
	public int getNextMY() {
		return nextMY;
	}

	/**
	 * @param nextMY the nextMY to set
	 */
	public void setNextMY(int nextMY) {
		this.nextMY = nextMY;
	}

	/**
	 * @return the currentPath
	 */
	public Path getCurrentPath() {
		return currentPath;
	}

	/**
	 * @param currentPath the currentPath to set
	 */
	public void setCurrentPath(Path currentPath) {
		this.currentPath = currentPath;
		setCurrentNode(currentPath.getFirst()); // maj du noeud courant
		isMoving = true; // l'entité se met en route (hop hop hop! =D)
	}

	/**
	 * @return the currentNode
	 */
	public PathNode getCurrentNode() {
		return currentNode;
	}

	/**
	 * @param currentNode the currentNode to set
	 */
	public void setCurrentNode(PathNode currentNode) {
		this.currentNode = currentNode;
		
		// ajout d'un peu d'alétoire pour pimenter la chose :)
		nextSX = currentNode.getS().x + Randomizer.getInstance().generateRangedInt(-5, 5);
		nextSY = currentNode.getS().y + Randomizer.getInstance().generateRangedInt(-5, 5);
	}
	
	
}
