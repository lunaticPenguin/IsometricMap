package resources;

import java.util.HashMap;

import org.newdawn.slick.Animation;


/**
 * Manager d'animations, selon les spritesheets d�j� charg�es
 * via le SpriteSheetManager.
 * 
 * @author Corentin Legros
 */
public class AnimationManager extends AbstractManager<Animation> {
	
	protected static AnimationManager instance;
	
	public static AnimationManager getInstance() {
		if (AnimationManager.instance == null) {
			instance = new AnimationManager();
		}
		return instance;
	}
	
	protected AnimationManager() {
		data = new HashMap<String, Animation>();
		
		// ici, r�utilisation des donn�es charg�es par SpriteSheetManager
		// et mise en m�moire des diff�rentes animations.
	}

	
	protected Animation loadObject(String dataObjectPath) {
		return null;
	}
}
