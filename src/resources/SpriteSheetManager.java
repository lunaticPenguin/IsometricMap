package resources;

import java.util.HashMap;

import org.newdawn.slick.SpriteSheet;


/**
 * Manager de spritesheets.
 * 
 * @author Corentin Legros
 */
public class SpriteSheetManager extends AbstractManager {
	
	protected static SpriteSheetManager instance;
	
	public static SpriteSheetManager getInstance() {
		if (instance == null) {
			instance = new SpriteSheetManager();
		}
		return instance;
	}
	
	protected SpriteSheetManager() {
		data = new HashMap<String, SpriteSheet>();
		
		this.loadData("spritesheet");
	}
}
