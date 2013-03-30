package resources;

import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;


/**
 * Manager de spritesheets.
 * 
 * @author Corentin Legros
 */
public class SpriteSheetManager extends AbstractManager<SpriteSheet> {
	
	protected static SpriteSheetManager instance;
	
	public static SpriteSheetManager getInstance() {
		if (instance == null) {
			instance = new SpriteSheetManager();
		}
		return instance;
	}
	
	
	protected SpriteSheetManager() {
		
		data = new HashMap<String, SpriteSheet>();
		this.loadData("data/spritesheets");
	}

	protected SpriteSheet loadObject(String dataObjectPath) {
		SpriteSheet objSprite = null;
		try {
			objSprite = new SpriteSheet(dataObjectPath, 50, 50);
		} catch (SlickException e) {
			Log.error("SpriteSheetManager : Error during loading spritesheet (" + dataObjectPath + " ): " + e.getMessage());
		}
		return objSprite;
	}
}
