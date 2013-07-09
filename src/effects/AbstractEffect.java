package effects;

import map.Camera;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import tools.Vector2i;


public abstract class AbstractEffect implements Comparable<AbstractEffect> {
	
	protected Animation animation;

	protected long duration;
	
	protected Vector2i displayingOffset;
	protected Vector2f s;
	
	boolean isPlaying;
	
	public AbstractEffect() {
		displayingOffset = new Vector2i();
		s = new Vector2f();
		isPlaying = false;
		param();
	}
	
	protected abstract void param();
	
	/**
	 * Permet de paramètrer convenablement l'effet
	 * @param Vector2f position position de l'effet
	 */
	public abstract void init(Vector2f position);
	
	/**
	 * Instant de fin (effet terminé ?)
	 */
	protected long endTime;
	
	public void render(Graphics g) {
		Camera cam = Camera.getInstance();
		animation.draw(cam.x + s.x + displayingOffset.x, cam.y + s.y + displayingOffset.y);
	}
	
	public void update(int delta) {
		
	}
	
	/**
	 * Permet de savoir si un effet est terminé
	 * @return
	 */
	public boolean isFinished() {
		return System.currentTimeMillis() > endTime;
	}
	
	/*
	 * Useless here.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(AbstractEffect o) {return 0;}
	
	/**
	 * Si l'effet est en train d'être joué
	 * @return
	 */
	public boolean isPlaying() {
		return isPlaying;
	}
	
	public void start() {
		isPlaying = true;
		endTime = System.currentTimeMillis() + duration;
	}
	
	public void stop() {
		s.x = 0;
		s.y = 0;
		
		isPlaying = false;
		endTime = 0;
	}
}
