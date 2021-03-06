package resources;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;


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
		
		SpriteSheetManager objSpSManager = SpriteSheetManager.getInstance();
		Set<String> spriteSheetKeys = objSpSManager.getAllKeys();
		
		Iterator<String> it = spriteSheetKeys.iterator();
		StringBuilder sbKey = new StringBuilder();
		while (it.hasNext()) {
			String key = it.next();
			for (int i = 0 ; i < 8 ; ++i) {
				sbKey.setLength(0);
				
				SpriteSheet tmpSpS = objSpSManager.get(key);
				sbKey.append(key).append("_").append(i);
				// new Animation(tmpSpS, x1, y1, x2, y2, horizontalScan, duration, autoUpdate)
				data.put(sbKey.toString(), new Animation(tmpSpS, 0, i, 13, i, true, 20, true));
			}
		}
	}

	/**
	 * Useless method in this manager!
	 */
	protected Animation loadObject(String dataObjectPath) {
		return null;
	}
}
