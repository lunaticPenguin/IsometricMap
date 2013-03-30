package resources;

import java.util.HashMap;

/**
 * Manager de ressources au sens large.
 * Permet de factoriser le code commun entre tous les manager
 * 
 * @author Corentin Legros
 */
public abstract class AbstractManager {
	
	/**
	 * Contient toutes les données relatives à un manager (sons, musiques, images...etc)
	 */
	protected HashMap<String, ?> data;
	
	/**
	 * Permet de charger les données selon le contenu des répertoires
	 * de données
	 */
	protected void loadData(String dataDirectoryPath) {
		
		// todo
	}
	
	/**
	 * Permet de renvoyer la ressource correspondante
	 * à la clé spécifiée.
	 * Chaque manager étant sensé contenant une ressource par défaut,
	 * si la ressource correspondante à clé spécifiée n'existe pas
	 * on essaye de renvoyer cette ressource. Sinon renvoit null.
	 * 
	 * @param String objectKey
	 * @return 
	 * @return Object|null
	 */
	public <T> Object get(String objectKey) {
		if (data.containsKey(objectKey)) {
			return data.get(objectKey);
		} else if (data.containsKey("default")) {
			return data.get("default");
		}
		return null;
	}
}
