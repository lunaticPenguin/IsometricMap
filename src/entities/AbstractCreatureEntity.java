package entities;

import java.util.Iterator;

import map.Camera;
import map.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import pathfinding.Path;
import pathfinding.PathNode;

import tools.Vector2i;

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
	
	public AbstractCreatureEntity() {
		super();
		nextNodeS = new Vector2i();
		nextNodeM = new Vector2i();
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
	
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		if (isMoving) {
			
			int offsetX = nextNodeS.x - s.x;
			int offsetY = nextNodeS.y - s.y;
			offsetY = offsetY != 0 ? offsetY : 1;
			
			double angle = Math.atan2(offsetY, offsetX) * 57.3;
			angle = (angle < 0) ? angle + 360 : angle;
			direction = ((int) Math.ceil(angle / 45) + 2) % 8; // 360 / 8 = 45
			
			int ratioMove = offsetX / offsetY;
			
			determineDirection();
			
			//s.x += delta * speedMove * 2 * ratioMove;
			//s.y += delta * speedMove * ratioMove;
		}
	}
	
	/**
	 * Permet d'afficher l'entité sur une zone de l'écran
	 * @param g
	 * @param cam
	 */
	public void draw(Graphics g, Camera cam) {
		g.drawLine(cam.x + s.x + offsetX, cam.y + s.y + offsetY, cam.x + s.x + zoneDim.x + offsetX, cam.y + s.y + offsetY);
		g.drawLine(cam.x + s.x + zoneDim.x + offsetX, cam.y + s.y + offsetY, cam.x + s.x + zoneDim.x + offsetX, cam.y + s.y + zoneDim.y + offsetY);
		g.drawLine(cam.x + s.x + zoneDim.x + offsetX, cam.y + s.y + zoneDim.y + offsetY, cam.x + s.x + offsetX, cam.y + s.y + zoneDim.y + offsetY);
		g.drawLine(cam.x + s.x + offsetX, cam.y + s.y + zoneDim.y + offsetY, cam.x + s.x + offsetX, cam.y + s.y + offsetY);
		
		
		renderCollidingZone(g, cam);
		
		// tmp: to render the unit path
		if (currentPath != null) {
			
			Iterator<PathNode> tmpIt = currentPath.iterator();
			PathNode tmpNode = null;
			
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
				tmpNode.renderCollidingZone(g, cam);
			}
		}
		
		if (isMoving) {
			this.getCurrentAnimation().draw(cam.x + s.x + offsetX, cam.y + s.y + offsetY);
		} else {
			this.getCurrentAnimation().getImage(0).draw(cam.x + s.x + offsetX, cam.y + s.y + offsetY);
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
			System.out.println("HE IS COLLIDING! :)");
			PathNode nextNode = currentNode.getNextNode();
			if (nextNode == null) {
				System.out.println("Arrivé à la fin :)");
				currentPath = null;
				isMoving = false;
			} else {
				m = currentNode.getM();
				setCurrentNode(nextNode);
				System.out.println("achieving node ("+m+"). Take a look for another node ("+nextNode.getM()+").");
				//System.out.println("(" + s + " -> " + nextNodeS + " (" +nextNodeM+ ")) - " + direction);
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
		nextNodeS.x = currentNode.getS().x;// + Randomizer.getInstance().generateRangedInt(-5, 5);
		nextNodeS.y = currentNode.getS().y;// + Randomizer.getInstance().generateRangedInt(-5, 5);
		nextNodeM = currentNode.getM();
	}
	
	
}
