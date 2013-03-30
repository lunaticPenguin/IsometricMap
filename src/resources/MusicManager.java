package resources;

import java.util.HashMap;

import org.newdawn.slick.Music;


/**
 * Manager de musiques.
 * 
 * @author Corentin Legros
 */
public class MusicManager extends AbstractManager<Music> {
	
	protected static MusicManager instance;
	
	public static MusicManager getInstance() {
		if (MusicManager.instance == null) {
			instance = new MusicManager();
		}
		return instance;
	}
	
	protected MusicManager() {
		data = new HashMap<String, Music>();
		
		this.loadData("musics");
	}
	
	
	protected Music loadObject(String dataObjectPath) {
		
		return null;
	}
}
