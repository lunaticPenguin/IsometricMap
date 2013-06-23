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
	 * Zone de collision si unité manquée
	 */
	protected SquareDetectionZone endZone;
	
	protected AbstractEntity target;
	
	protected SpriteSheet sprite;
	
	protected boolean isDestroyed;
	
	/**
	 * Méthode d'initialisation
	 */
	public void init(Vector2f screenPositionStart, Vector2f screenPositionStop) {
		s.x = screenPositionStart.x;
		s.y = screenPositionStart.y;
		
		endZone = new SquareDetectionZone();
		endZone.setS(screenPositionStop);
		endZone.setZoneDim(new Vector2i(100, 100));
		endZone.setZoneOffset(-50, -50); // p-ê en positif, je sais plus trop!
		
		offset = new Vector2i((int) (screenPositionStop.x - screenPositionStart.x),
			(int) (screenPositionStop.y - screenPositionStop.y));
		ratioTrajectory = new Vector2f();
		
		// calcul du ratio pour les déplacements
		double distance = Math.sqrt(Math.pow((double)offset.x, 2) + Math.pow((double)offset.y, 2));
		
		ratioTrajectory.x = (float) (offset.x/distance);
		ratioTrajectory.y = (float) (offset.y/distance);
		
		isDestroyed = false;
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
			} else if (isColliding(endZone)) {
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
}
