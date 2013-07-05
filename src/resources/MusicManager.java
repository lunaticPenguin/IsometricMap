package resources;

import java.util.HashMap;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;


/**
 * Manager de musiques.
 * 
 * @author Corentin Legros
 */
public class MusicManager extends AbstractManager<Music> {
	
	protected String strCurrentMusic = null;
	
	protected static MusicManager instance;
	
	public static MusicManager getInstance() {
		if (MusicManager.instance == null) {
			instance = new MusicManager();
		}
		return instance;
	}
	
	protected MusicManager() {
		data = new HashMap<String, Music>();
		
		this.loadData("data/musics");
	}
	
	
	protected Music loadObject(String dataObjectPath) {
		Music tmpMusic = null;
		try {
			tmpMusic = new Music(dataObjectPath);
		} catch (SlickException e) {
			Log.warn("Unable to instanciate a new music (" + dataObjectPath + ")");
		}
		return tmpMusic;
	}
	
	/**
	 * Permet de jouer la musique dont la clé est spécifiée en paramètre.
	 * La musique ne boucle pas
	 * 
	 * @param strMusicName
	 */
	public void playMusic(final String strMusicName) {
		playMusic(strMusicName, false);
	}
	
	/**
	 * Permet de jouer la musique dont la clé est spécifiée en paramètre
	 * @param String strMusicName
	 * @param boolean shouldLoop si la musique doit boucler
	 */
	public void playMusic(final String strMusicName, boolean shouldLoop) {
		if (strCurrentMusic != null) {
			get(strCurrentMusic).stop();
		}
		if (shouldLoop) {
			get(strMusicName).loop();
		} else {
			get(strMusicName).play();
		}
	}
	
	/**
	 * Permet d'arrêter la lecture de la musique courante
	 */
	public void stopCurrentMusic () {
		if (strCurrentMusic != null) {
			get(strCurrentMusic).stop();
		} else {
			Log.warn("Receive stop order for music (" + strCurrentMusic + "), but there is no music running.");
		}
	}
}
