package resources;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.newdawn.slick.util.Log;

/**
 * Manager de ressources au sens large.
 * Permet de factoriser le code commun entre tous les manager
 * 
 * @author Corentin Legros
 */
public abstract class AbstractManager<T> {
	
	/**
	 * Contient toutes les données relatives à un manager (sons, musiques, images...etc)
	 */
	protected HashMap<String, T> data;
	
	/**
	 * Permet de charger les données selon le contenu des répertoires
	 * de données, de façon récursive.
	 * Attention, l'arborescence détermine les clés d'accès aux données.
	 */
	protected void loadData(String dataDirectoryPath) {
		File specificDataDirectory = new File(dataDirectoryPath);
		
		File[] specificFiles = specificDataDirectory.listFiles();
		
		StringBuilder fileName = new StringBuilder();
		StringBuilder sbObjectKey = new StringBuilder();
		
		for (int i = 0 ; i < specificFiles.length ; ++i) {
			
			fileName.setLength(0);
			try {
				fileName.append(specificFiles[i].getCanonicalPath());
				
				if (specificFiles[i].isDirectory()) { // si répertoire -> récursivité
					this.loadData(specificFiles[i].getPath());
				} else if (!specificFiles[i].isHidden() && specificFiles[i].isFile()) {
					
					sbObjectKey.setLength(0);
					String tmpPath = specificFiles[i].getPath();
					String[] tmpKeys = tmpPath.split(Pattern.quote(File.separator));
					if (tmpKeys.length > 2) {
						for (int j = 2 ; j < tmpKeys.length ; ++j) {
							sbObjectKey.append(tmpKeys[j]);
							if (j < tmpKeys.length - 1) {
								sbObjectKey.append(".");
							}
						}
					} else {
						Log.warn("AbstractManager.load() : Unable to split path key (" + tmpPath + ")");
					}
					
					data.put(sbObjectKey.toString(), this.loadObject(fileName.toString()));
				}
			} catch (IOException e) {
				Log.warn("AbstractManager.load() : Unable to get file canonical path  (" + specificFiles[i].getPath() + ") : " + e.getMessage());
			}
		}
		
		return;
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
	 * @return 
	 * @return Object|null
	 */
	public T get(final String objectKey) {
		if (data.containsKey(objectKey)) {
			return data.get(objectKey);
		} else if (data.containsKey("default")) {
			return data.get("default");
		}
		return null;
	}
	
	/**
	 * Permet aux manager enfants de charger un objet de leur type géré
	 * au parent, car le parent ne fait que du générique.
	 * 
	 * @param String dataObjectPath
	 * @return
	 */
	abstract protected T loadObject(String dataObjectPath);
}
