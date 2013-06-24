package resources;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.util.Log;


/**
 * Manager d'émetteurs de particules.
 * 
 * @author Corentin Legros
 */
public class EmitterManager extends AbstractManager<ConfigurableEmitter> {
	
	protected static EmitterManager instance;
	
	public static EmitterManager getInstance() {
		if (instance == null) {
			instance = new EmitterManager();
		}
		return instance;
	}
	
	
	protected EmitterManager() {
		
		data = new HashMap<String, ConfigurableEmitter>();
		this.loadData("data/emitters");
	}

	protected ConfigurableEmitter loadObject(String dataObjectPath) {
		ConfigurableEmitter objEmitter = null;
		File tmpFile = new File(dataObjectPath);
		try {
			objEmitter = ParticleIO.loadEmitter(tmpFile);
		} catch (IOException e) {
			Log.error("EmitterManager : Error during loading ConfigurableEmitter (" + dataObjectPath + " ): " + e.getMessage());
		}
		return objEmitter;
	}
	
	public Collection<ConfigurableEmitter> getAllFiles() {
		return data.values();
	}
	
	public Set<String> getAllKeys() {
		return data.keySet();
	}
}
