package entities.manager;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

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
	protected List<T> dataHidden;
	
	/**
	 * Permet d'ajouter une entité et d'accéder à sa référence
	 * @param String entityType
	 * @return T entity
	 */
	public T addEntity(String entityType) {
		T entity = factory.getEntity(entityType);
		data.add(entity);
		Collections.sort(data);
		return entity;
	}
	
	/**
	 * Permet d'enlever une entité utilisé dans l'application
	 * et de la restocker dans la pool correspondante.
	 * 
	 * @param entityType
	 * @param entity
	 */
	public void removeEntity(String entityType, T entity) {
	
		int indexToRemove = data.indexOf(entity);
		if (indexToRemove != -1) {
			data.remove(entity);
			factory.setEntityBack(entityType, entity);
		} else {
			Log.warn("AbstractManager.removeEntity() : No specified data of : " + entityType + " type");
		}
	}
	
	
	/**
	 * Permet de mettre à jour l'ensemble des entités en cours dans l'application
	 * 
	 * @param container
	 * @param game
	 * @param delta
	 */
	public abstract void update(GameContainer container, StateBasedGame game, int delta);
	
	/**
	 * Méthode permettant de rendre seulement les entités présents sur une ligne.
	 * Permet du coup un affichage trié
	 * 
	 * @param Graphics g
	 * @param Camera cam
	 */
	public void render(Graphics g) {
		
		Iterator<T> dataIterator = data.iterator();
		while (dataIterator.hasNext()) {
			this.renderEntity(g, dataIterator.next());
		}
	}
	
	protected abstract void renderEntity(Graphics g, T entity);
}
