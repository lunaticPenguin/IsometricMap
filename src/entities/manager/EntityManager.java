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

import entities.AbstractEntity;
import entities.factory.EntityFactory;

public class EntityManager extends AbstractManager<AbstractEntity> {
	
	protected static EntityManager instance;
	
	public static EntityManager getInstance() {
		if (instance == null) {
			instance = new EntityManager();
		}
		return instance;
	}
	
	protected EntityManager() {
		factory = EntityFactory.getInstance();
		
		data = new HashMap<String, ArrayList<AbstractEntity>>();
		data.put(EntityFactory.ENTITY_CREATUREJIDIAKO, new ArrayList<AbstractEntity>());
		data.put(EntityFactory.ENTITY_TOWERGUARD, new ArrayList<AbstractEntity>());
	}

	@Override
	protected void renderEntity(Graphics g, Camera cam, AbstractEntity entity) {
		if (entity.belongToRenderedAera(cam)) {
			entity.draw(g, cam);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		Set<Entry<String, ArrayList<AbstractEntity>>> entitiesSet = data.entrySet();
		Iterator<Entry<String, ArrayList<AbstractEntity>>> entitiesIterator = entitiesSet.iterator();
		
		Entry<String, ArrayList<AbstractEntity>> pairEntityCollection = null;
		Iterator<AbstractEntity> entityTypeIterator = null;
		AbstractEntity entityType = null;
		
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
