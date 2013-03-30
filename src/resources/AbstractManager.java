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
	 * Contient toutes les donn�es relatives � un manager (sons, musiques, images...etc)
	 */
	protected HashMap<String, ?> data;
	
	/**
	 * Permet de charger les donn�es selon le contenu des r�pertoires
	 * de donn�es
	 */
	protected void loadData(String dataDirectoryPath) {
		
		// todo
	}
	
	/**
	 * Permet de renvoyer la ressource correspondante
	 * � la cl� sp�cifi�e.
	 * Chaque manager �tant sens� contenant une ressource par d�faut,
	 * si la ressource correspondante � cl� sp�cifi�e n'existe pas
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
