package resources;

import java.util.HashMap;

import org.newdawn.slick.Animation;


/**
 * Manager d'animations, selon les spritesheets déjà chargées
 * via le SpriteSheetManager.
 * 
 * @author Corentin Legros
 */
public class AnimationManager extends AbstractManager {
	
	protected static AnimationManager instance;
	
	public static AnimationManager getInstance() {
		if (AnimationManager.instance == null) {
			instance = new AnimationManager();
		}
		return instance;
	}
	
	protected AnimationManager() {
		data = new HashMap<String, Animation>();
		
		// ici, réutilisation des données chargées par SpriteSheetManager
		// et mise en mémoire des différentes animations.
	}
}
