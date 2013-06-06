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

import entities.AbstractBuildingEntity;
import entities.factory.BuildingFactory;

public class BuildingManager extends AbstractManager<AbstractBuildingEntity> {
	
	protected static BuildingManager instance;
	
	public static BuildingManager getInstance() {
		if (instance == null) {
			instance = new BuildingManager();
		}
		return instance;
	}
	
	protected BuildingManager() {
		factory = BuildingFactory.getInstance();
		data = new HashMap<String, ArrayList<AbstractBuildingEntity>>();
		data.put("jidiako", new ArrayList<AbstractBuildingEntity>());
	}

	@Override
	protected void renderEntity(Graphics g, Camera cam, AbstractBuildingEntity entity) {
		if (entity.belongToRenderedAera(cam)) {
			entity.draw(g, cam);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		Set<Entry<String, ArrayList<AbstractBuildingEntity>>> entitiesSet = data.entrySet();
		Iterator<Entry<String, ArrayList<AbstractBuildingEntity>>> entitiesIterator = entitiesSet.iterator();
		
		Entry<String, ArrayList<AbstractBuildingEntity>> pairEntityCollection = null;
		Iterator<AbstractBuildingEntity> entityTypeIterator = null;
		AbstractBuildingEntity entityType = null;
		
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
