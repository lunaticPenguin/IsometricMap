package entities.types.projectiles;

import map.Camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import tools.Vector2i;

import collision.SquareDetectionZone;
import entities.AbstractEntity;
import entities.IOffensive;

public abstract class AbstractProjectile extends SquareDetectionZone implements IOffensive, Comparable<AbstractProjectile> {
	
	/**
	 * Permet de connaitre l'offset entre le pt start et stop
	 */
	protected Vector2i offset;
	
	/**
	 * Permet de connaitre le ratio du déplacement (calculé seulement une fois)
	 */
	protected Vector2f ratioTrajectory;
	
	protected float speedMove;
	
	/**
	 * entity attack points
	 */
	protected float attack;
	
	/**
	 * Rayon d'action des dégâts lors de l'impact (<=> collision avec endZone)
	 */
	protected int actionRange;
	
	/**
	 * Limite de collision si unité manquée
	 */
	protected Vector2i endLimit;
	
	protected AbstractEntity target;
	
	protected SpriteSheet sprite;
	
	protected boolean isDestroyed;
	
	/**
	 * Méthode d'initialisation
	 */
	public void init(AbstractEntity sender, AbstractEntity receiver) {
		
		s = sender.getFirePoint();
		
		offset = new Vector2i((int) (receiver.getS().x - s.x),
			(int) (receiver.getS().y - s.y));
		ratioTrajectory = new Vector2f();
		
		endLimit = new Vector2i();
		if (offset.x > 0) {
			endLimit.x = (int) receiver.getS().x + receiver.getZoneDim().x;
		} else {
			endLimit.x = (int) receiver.getS().x - receiver.getZoneDim().x;
		}
		
		if (offset.y > 0) {
			endLimit.y = (int) receiver.getS().y + receiver.getZoneDim().y;
		} else {
			endLimit.y = (int) receiver.getS().y - receiver.getZoneDim().y;
		}
		
		// calcul du ratio pour les déplacements
		double distance = Math.sqrt(Math.pow((double)offset.x, 2) + Math.pow((double)offset.y, 2));
		
		ratioTrajectory.x = (float) (offset.x/distance);
		ratioTrajectory.y = (float) (offset.y/distance);
		
		isDestroyed = false;
		
		target = receiver;
	}
	
	/**
	 * Met à jour le projectile
	 * 
	 * @param container
	 * @param game
	 * @param delta
	 * @return boolean is projectile NOT destroyed
	 */
	public boolean update(GameContainer container, StateBasedGame game, int delta) {
		
		s.x += delta * speedMove * ratioTrajectory.x;
		s.y += delta * speedMove * ratioTrajectory.y;
		
		if (!isDestroyed) {
			if (isColliding(target)) {
				attack(target);
			} else if (this.checkForOutPassingTarget()) {
				isDestroyed = true;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Permet d'afficher le projectile
	 * @param g
	 */
	public void draw(Graphics g) {
		Camera cam = Camera.getInstance();
		sprite.draw(cam.x + s.x + displayingOffset.x, cam.y + s.y + displayingOffset.y);
		
		if (isDestroyed) {
			// todo : super effet de la mort qui tue!
		}
	}
	
	@Override
	public void attack(AbstractEntity entityToAttack) {
		
	}
	
	/**
	 * Permet de vérifier si le projectile a dépassé sa cible
	 * @return
	 */
	private boolean checkForOutPassingTarget() {
		if (ratioTrajectory.x <= 0 && ratioTrajectory.y <= 0) {
			return s.x < endLimit.x || s.y < endLimit.y;
		} else if (ratioTrajectory.x >= 0 && ratioTrajectory.y <= 0) {
			return s.x > endLimit.x || s.y < endLimit.y;
		} else if (ratioTrajectory.x <= 0 && ratioTrajectory.y >= 0) {
			return s.x < endLimit.x || s.y > endLimit.y;
		} else if (ratioTrajectory.x >= 0 && ratioTrajectory.y >= 0) {
			return s.x > endLimit.x || s.y > endLimit.y;
		}
		return false;
	}
}
