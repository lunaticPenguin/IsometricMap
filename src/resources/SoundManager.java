package resources;

import java.util.HashMap;

import org.newdawn.slick.Sound;


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
		
		this.loadData("sounds");
	}
	
	protected Sound loadObject(String dataObjectPath) {
		return null;
	}
}
