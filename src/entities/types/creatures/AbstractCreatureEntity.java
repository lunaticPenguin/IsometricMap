package entities.types.creatures;

import java.util.Iterator;

import main.MyGame;
import map.Camera;
import map.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import entities.AbstractEntity;
import entities.IMoveable;
import entities.IOffensive;

import pathfinding.Path;
import pathfinding.PathNode;

import tools.Randomizer;
import tools.Vector2i;

public abstract class AbstractCreatureEntity extends AbstractEntity implements IMoveable, IOffensive {
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
	
	/**
	 * Positions pixels
	 * du prochain noeud de passage
	 */
	protected Vector2i nextNodeS;
	
	/**
	 * Positions orthogonales
	 * du prochain noeud de passage
	 */
	protected Vector2i nextNodeM;
	
	protected Path currentPath;
	protected PathNode currentNode;
	

	private int offsetX;
	private int offsetY;
	private double ratioMoveX;
	private double ratioMoveY;
	private boolean boolNorth;
	private boolean boolSouth;
	private boolean boolWest;
	private boolean boolEast;
	
	
	public AbstractCreatureEntity() {
		super();
		nextNodeS = new Vector2i();
		nextNodeM = new Vector2i();
		
		// point de tir commun
		firePoint = new Vector2i(2, -15);
	}
	
	
	/**
	 * Si l'entité se déplace
	 */
	public boolean isMoving() {
		return isMoving;
	}
	
	public void setIsMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	public boolean update(GameContainer container, StateBasedGame game, int delta) {
		
		if (isMoving) {
			
			determineDirection();
			float y = s.y;
			s.x += delta * speedMove * ratioMoveX;
			s.y += delta * speedMove * ratioMoveY;
			return y != s.y; // il n'y a indication du tri que si modification du y
		} else {
			
			// code de base pour attaque des créatures
			if (target != null && System.currentTimeMillis() > nextTimeForShot) {
				attack(target);
				nextTimeForShot = System.currentTimeMillis() + reloadDuration;
			}
			if (target != null && target.isDead()) {
				target = null;
			}
		}
		return false;
	}
	
	/**
	 * Permet d'afficher l'entité sur une zone de l'écran
	 * @param g
	 * @param cam
	 */
	public void draw(Graphics g) {
		Camera cam = Camera.getInstance();
		g.drawOval(cam.x + s.x - (actionRange * Map.tDim.x), cam.y + s.y - (actionRange * Map.tDim.y), actionRange * Map.tDim.x * 2, actionRange * Map.tDim.y * 2);
		
		Iterator<PathNode> tmpIt = currentPath.iterator();
		PathNode tmpNode = null;
		
		
		if (MyGame.getDebugging()) {
			while (tmpIt.hasNext()) {
				tmpNode = tmpIt.next();
				
				g.drawLine(cam.x + tmpNode.getS().x, cam.y + tmpNode.getS().y - Map.mTDim.y, cam.x + tmpNode.getS().x + Map.mTDim.x, cam.y + tmpNode.getS().y);
				g.drawLine(cam.x + tmpNode.getS().x + Map.mTDim.x, cam.y + tmpNode.getS().y, cam.x + tmpNode.getS().x, cam.y + tmpNode.getS().y + Map.mTDim.y);
				g.drawLine(cam.x + tmpNode.getS().x, cam.y + tmpNode.getS().y + Map.mTDim.y, cam.x + tmpNode.getS().x - Map.mTDim.x, cam.y + tmpNode.getS().y);
				g.drawLine(cam.x + tmpNode.getS().x - Map.mTDim.x, cam.y + tmpNode.getS().y, cam.x + tmpNode.getS().x, cam.y + tmpNode.getS().y - Map.mTDim.y);
				g.drawLine(
					cam.x + tmpNode.getS().x, cam.y + tmpNode.getS().y,
					cam.x + tmpNode.getS().x, cam.y + tmpNode.getS().y
				);
			}
		}
		
		if (isMoving) {
			this.getCurrentAnimation().draw(cam.x + s.x + displayingOffset.x, cam.y + s.y + displayingOffset.y);
		} else {
			this.getCurrentAnimation().getImage(0).draw(cam.x + s.x + displayingOffset.x, cam.y + s.y + displayingOffset.y);
		}
		
		if (highlight) {
			renderCollidingZone(g);
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
		
		if (this.isColliding(currentNode)) {
			
			PathNode nextNode = currentNode.getNextNode();
			if (nextNode == null) {
				currentPath = null;
				isMoving = false;
			} else {
				
				m = currentNode.getM();
				setCurrentNode(nextNode);
				
				offsetX = nextNodeS.x -(int) s.x;
				offsetY = nextNodeS.y - (int) s.y;
				
				offsetX = offsetX != 0 ? offsetX : 1;
				offsetY = offsetY != 0 ? offsetY : 1;
				
				// calcul du ratio pour les déplacements
				double distance = Math.sqrt(Math.pow((double)offsetX, 2) + Math.pow((double)offsetY, 2));
				ratioMoveX = offsetX/distance;
				ratioMoveY = offsetY/distance;
				
				boolNorth = offsetY + 5 < 0;
				boolSouth = offsetY - 5 > 0;
				boolWest = offsetX + 10 < 0;
				boolEast = offsetX - 10 > 0;
				
				if (!boolNorth && !boolSouth) {
					if (boolEast) {
						direction = DIRECTION_EAST;
					}
					if (boolWest) {
						direction = DIRECTION_WEST;
					}
				} else {
					if (boolNorth && boolWest) {
						ratioMoveX = -ratioMoveX;
						ratioMoveY = -ratioMoveY;
						direction = DIRECTION_NORTHWEST;
					} else if (boolNorth && boolEast) {
						ratioMoveX = -ratioMoveX;
						direction = DIRECTION_NORTHEAST;
					} else if (boolSouth && boolWest) {
						ratioMoveY = -ratioMoveY;
						direction = DIRECTION_SOUTHWEST;
					} else if (boolSouth && boolEast) {
						direction = DIRECTION_SOUTHEAST;
					}
				}
				if (!boolEast && !boolWest) {
					if (boolNorth) {
						direction = DIRECTION_NORTH;
					}
					if (boolSouth) {
						direction = DIRECTION_SOUTH;
					}
				} else {
					if (boolNorth && boolWest) {
						ratioMoveX = -ratioMoveX;
						ratioMoveY = -ratioMoveY;
						direction = DIRECTION_NORTHWEST;
					} else if (boolNorth && boolEast) {
						ratioMoveX = -ratioMoveX;
						direction = DIRECTION_NORTHEAST;
					} else if (boolSouth && boolWest) {
						ratioMoveY = -ratioMoveY;
						direction = DIRECTION_SOUTHWEST;
					} else if (boolSouth && boolEast) {
						direction = DIRECTION_SOUTHEAST;
					}
				}
			}
		}
	}

	


	/**
	 * @return the nextNodeS
	 */
	public Vector2i getNextNodeS() {
		return nextNodeS;
	}

	/**
	 * @param nextNodeS the nextNodeS to set
	 */
	public void setNextNodeS(Vector2i nextNodeS) {
		this.nextNodeS = nextNodeS;
	}

	/**
	 * @return the nextNodeM
	 */
	public Vector2i getNextNodeM() {
		return nextNodeM;
	}

	/**
	 * @param nextNodeM the nextNodeM to set
	 */
	public void setNextNodeM(Vector2i nextNodeM) {
		this.nextNodeM = nextNodeM;
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
		
		if (currentPath == null || currentPath.isEmpty()) {
			return;
		}
		this.currentPath = currentPath;
		
		// si la case de départ != de la case de fin
		if (currentPath.peekFirst() != currentPath.peekLast()) {
			setCurrentNode(currentPath.getFirst()); // maj du noeud courant
			isMoving = true; // l'entité se met en route (hop hop hop! =D)
		}
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
		nextNodeS.x = (int) currentNode.getS().x + Randomizer.getInstance().generateRangedInt(-5, 5);
		nextNodeS.y = (int) currentNode.getS().y + Randomizer.getInstance().generateRangedInt(-5, 5);
		nextNodeM = currentNode.getM();
	}
	
	/**
	 * Code d'attaque basique
	 * {@inheritDoc}
	 */
	public void attack(AbstractEntity entity) {
		entity.defend(attackPoints);
	}
}
