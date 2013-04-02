package entities.manager;

import java.util.ArrayList;
import java.util.HashMap;

import map.Camera;

import org.newdawn.slick.Graphics;

import entities.AbstractCreatureEntity;
import entities.factory.CreatureFactory;

public class CreatureManager extends AbstractManager<AbstractCreatureEntity> {
	
	protected static CreatureManager instance;
	
	public static CreatureManager getInstance() {
		if (instance == null) {
			instance = new CreatureManager();
		}
		return instance;
	}
	
	protected CreatureManager() {
		factory = CreatureFactory.getInstance();
		data = new HashMap<String, ArrayList<AbstractCreatureEntity>>();
		data.put("jidiako", new ArrayList<AbstractCreatureEntity>());
	}

	@Override
	protected void renderEntity(Graphics g, Camera cam, AbstractCreatureEntity entity) {
		if (entity.belongToRenderedAera(cam)) {
			entity.draw(g, cam);
		}
	}
}
