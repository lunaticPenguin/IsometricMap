package entities.factory;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.util.Log;

/**
 * Factory abstraite jouant le rôle de pool
 * @author Corentin Legros
 *
 * @param <T>
 */

public abstract class AbstractFactory<T> {
	
	/**
	 * Contient les entités correspondantes selon la fabrique
	 */
	protected HashMap<String, ArrayList<T>> dataPool;
	
	/**
	 * Méthode allant chercher une entité dans la pool
	 * @param String entity type name (key)
	 * @return T entity type
	 */
	public T getEntity(String entityType) {
		if (dataPool.containsKey(entityType)) {
			int size = dataPool.get(entityType).size();
			// si non vide, 
			if (size != 0) {
				return dataPool.get(entityType).remove(size - 1);
			}
			return getSpecificEntity(entityType);
		} else {
			Log.warn("AbstractFactory.getEntity() : Wrong type given.");
		}
		return null;
	}
	
	
	public boolean setEntityBack(String entityType, T entity) {
		if (dataPool.containsKey(entityType)) {
			return dataPool.get(entityType).add(entity);
		} else {
			Log.warn("AbstractFactory.getEntityBack() : Wrong type given.");
		}
		return false;
	}
	
	abstract protected T getSpecificEntity(String entityType);
}
