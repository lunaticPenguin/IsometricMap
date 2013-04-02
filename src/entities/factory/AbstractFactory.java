package entities.manager;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AbstractManager<T> {
	
	/**
	 * Contient les entités correspondantes selon le manager
	 */
	protected HashMap<String, ArrayList<T>> data;
}
