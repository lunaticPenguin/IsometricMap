package resources;

import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.util.Log;


/**
 * Manager de sons.
 * 
 * @author Corentin Legros
 */
public class SoundManager extends AbstractManager<Sound> {
	
	protected static SoundManager instance;
	
	public static SoundManager getInstance() {
		if (SoundManager.instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}
	
	protected SoundManager() {
		data = new HashMap<String, Sound>();
		
		this.loadData("data/sounds");
	}
	
	protected Sound loadObject(String dataObjectPath) {
		Sound tmpSound= null;
		try {
			tmpSound = new Sound(dataObjectPath);
		} catch (SlickException e) {
			Log.warn("Unable to instanciate a new sound (" + dataObjectPath + ")");
		}
		return tmpSound;
	}
}
