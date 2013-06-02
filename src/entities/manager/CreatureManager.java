package entities.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import map.Camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

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

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		Set<Entry<String, ArrayList<AbstractCreatureEntity>>> entitiesSet = data.entrySet();
		Iterator<Entry<String, ArrayList<AbstractCreatureEntity>>> entitiesIterator = entitiesSet.iterator();
		
		Entry<String, ArrayList<AbstractCreatureEntity>> pairEntityCollection = null;
		Iterator<AbstractCreatureEntity> entityTypeIterator = null;
		AbstractCreatureEntity entityType = null;
		
		while (entitiesIterator.hasNext()) {
			pairEntityCollection = entitiesIterator.next();
			entityTypeIterator = pairEntityCollection.getValue().iterator();
			while (entityTypeIterator.hasNext()) {
				entityType = entityTypeIterator.next();
				entityType.update(container, game, delta);
			}
		}
	}
}
