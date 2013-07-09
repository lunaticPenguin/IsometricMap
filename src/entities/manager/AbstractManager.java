package entities.manager;

import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import entities.factory.AbstractFactory;

/**
 * Classe générique de manager d'entités.
 * Permet de gérer simplement les entités simplement
 * en cours dans l'application.
 * Joue également le rôle de façade avec les factories/pools.
 * 
 * @author Corentin Legros
 *
 * @param <T>
 */
public abstract class AbstractManager<T extends Comparable<? super T>> {
	
	protected AbstractFactory<T> factory;
	
	protected Iterator<T> iterator;

	/**
	 * Contient les entités correspondantes selon le manager
	 */
	protected List<T> data;
	
	/**
	 * Permet d'ajouter une entité et d'accéder à sa référence
	 * @param String entityType
	 * @return T entity
	 */
	public T addEntity(String entityType) {
		T entity = factory.getEntity(entityType);
		data.add(entity);
		return entity;
	}
	
	/**
	 * Permet de mettre à jour l'ensemble des entités en cours dans l'application
	 * 
	 * @param container
	 * @param game
	 * @param delta
	 */
	public abstract void update(GameContainer container, StateBasedGame game, int delta, Graphics g);
	
	protected abstract void renderEntity(Graphics g, T entity);
}
