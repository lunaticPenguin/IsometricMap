package entities.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
	 * TODO
	 * Permet de mettre à jour l'ensemble des entités en cours dans l'application
	 * 
	 * @param container
	 * @param game
	 * @param delta
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
	}
	
	
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
		
		// todo: Parcourir la hashmap complète.
		iterator = data.get("jidiako").iterator();
		while (iterator.hasNext()) {
			T entity = iterator.next();
			this.renderEntity(g, cam, entity);
		}
	}
	
	
	protected abstract void renderEntity(Graphics g, Camera cam, T entity);
}
