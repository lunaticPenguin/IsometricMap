package entities.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import map.Camera;

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
public abstract class AbstractManager<T> {
	
	protected AbstractFactory<T> factory;
	
	
	protected Iterator<T> iterator;
	
	/**
	 * Contient les entités correspondantes selon le manager
	 */
	protected HashMap<String, ArrayList<T>> data;
	
	/**
	 * Permet d'ajouter une entité et d'accéder à sa référence
	 * @param String entityType
	 * @return T entity
	 */
	public T addEntity(String entityType) {
		
		if (data.containsKey(entityType)) {
			T entity = factory.getEntity(entityType);
			data.get(entityType).add(entity);
			return entity;
		} else {
			Log.warn("AbstractManager.addEntity() : Wrong type given.");
		}
		return null;
	}
	
	/**
	 * Permet d'enlever une entité utilisé dans l'application
	 * et de la restocker dans la pool correspondante.
	 * 
	 * @param entityType
	 * @param entity
	 */
	public void removeEntity(String entityType, T entity) {
		if (data.containsKey(entityType)) {
			int indiceToRemove;
			if ((indiceToRemove = data.get(entityType).indexOf(entity)) != -1) {
				entity = data.get(entityType).remove(indiceToRemove);
				factory.setEntityBack(entityType, entity);
			}
		} else {
			Log.warn("AbstractManager.addEntity() : Wrong type given.");
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
	 * TODO
	 * Permet d'afficher l'ensemble des entités utilisées dans l'application
	 * 
	 * @param GameContainer container
	 * @param StateBasedGame game
	 * @param Graphics g
	 * @param Camera cam
	 */
	public void render(Graphics g, Camera cam) {
		
		Set<Entry<String, ArrayList<T>>> typeEntrySet = data.entrySet();
		Iterator<Entry<String, ArrayList<T>>> iteratorSet = typeEntrySet.iterator();
		Entry<String, ArrayList<T>> typeListEntry = null;
		ArrayList<T> entitiesList = null;
		Iterator<T> entitiesIterator = null;
		
		while (iteratorSet.hasNext()) {
			typeListEntry = iteratorSet.next();
			entitiesList = typeListEntry.getValue();
			entitiesIterator = entitiesList.iterator();
			while (entitiesIterator.hasNext()) {
				this.renderEntity(g, cam, entitiesIterator.next());
			}
		}
	}
	
	
	protected abstract void renderEntity(Graphics g, Camera cam, T entity);
}
