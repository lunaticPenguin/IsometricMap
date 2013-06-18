package entities.manager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
	 * 
	 * KEY: hashCode
	 * VALUE : TYPE T
	 */
	protected HashMap<Integer, T> data;
	
	/**
	 * Contient les entités correspondantes selon le manager
	 * 
	 * KEY : Y index (z-order)
	 * VALUE : MAP (KEY: hashCode, VALUE: TYPE T) des entités concernées par l'index
	 */
	protected HashMap<Integer, HashMap<Integer, T>> dataZOrder;
	
	/**
	 * Stocke les positions inverse :
	 * on les retrouve via le hashCode des T
	 * 
	 * KEY: hashCode
	 * VALUE: Y index
	 */
	protected HashMap<Integer, Integer> dataZOrderReverse;
	
	/**
	 * Permet d'ajouter une entité et d'accéder à sa référence
	 * @param String entityType
	 * @return T entity
	 */
	public T addEntity(String entityType) {
		T entity = factory.getEntity(entityType);
		int hashCode = entity.hashCode();
		data.put(hashCode, entity);
		
		dataZOrder.get(0).put(hashCode, entity);
		dataZOrderReverse.put(hashCode, 0);
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

		int hashCode = entity.hashCode();
		
		if (data.containsKey(hashCode)) {
			if (data.containsKey(entity.hashCode())) {
				int index = dataZOrderReverse.get(hashCode);
				dataZOrder.get(index).remove(hashCode);
				data.remove(hashCode);
				factory.setEntityBack(entityType, entity);
			} else {
				Log.warn("AbstractManager.removeEntity() : No specified data : " + hashCode);
			}
		} else {
			Log.warn("AbstractManager.removeEntity() : Wrong type given.");
		}
	}
	
	/**
	 * Permet de changer d'ordre une entité
	 * @param T entity
	 * @param int newOrder
	 */
	public void setEntityNewOrder(T entity, int newOrder) {
		
		int hashCode = entity.hashCode();
		if (!data.containsKey(hashCode)) {
			return;
		}
		
		int lastOrder = dataZOrderReverse.get(entity.hashCode());
		if (lastOrder == newOrder) {
			return;
		}
		
		dataZOrder.get(lastOrder).remove(hashCode);
		dataZOrder.get(newOrder).put(hashCode, entity);
		dataZOrderReverse.put(hashCode, newOrder);
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
	 * @param int numRow
	 */
	public void renderSpecificRow(Graphics g, Camera cam, int numRow) {
		
		if (dataZOrder.get(numRow).isEmpty()) {
			return;
		}
		
		Iterator<Entry<Integer, T>> dataEntryRowIterator = dataZOrder.get(numRow).entrySet().iterator();
		Entry<Integer, T> dataIterator = null;
		
		
		while (dataEntryRowIterator.hasNext()) {
			dataIterator = dataEntryRowIterator.next();
			this.renderEntity(g, cam, dataIterator.getValue());
		}
	}
	
	protected abstract void renderEntity(Graphics g, Camera cam, T entity);
}
