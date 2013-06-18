package entities.manager;

import java.util.HashMap;
import java.util.Iterator;

import java.util.Map.Entry;

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
		
		data = new HashMap<Integer, AbstractEntity>();
		dataZOrder = new HashMap<Integer, HashMap<Integer, AbstractEntity>>();
		
		int max = map.Map.mDim.x;
		for (int i = 0 ; i < max ; ++i) {
			dataZOrder.put(i, new HashMap<Integer, AbstractEntity>());
		}
		
		dataZOrderReverse = new HashMap<Integer, Integer>();
	}

	@Override
	protected void renderEntity(Graphics g, Camera cam, AbstractEntity entity) {
		if (entity.belongToRenderedAera(cam)) {
			entity.draw(g, cam);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		Iterator<Entry<Integer, AbstractEntity>> dataEntryIterator = data.entrySet().iterator();
		Entry<Integer, AbstractEntity> dataIterator;
		AbstractEntity entity = null;
		
		int newOrder = 0;
		while (dataEntryIterator.hasNext()) {
			dataIterator = dataEntryIterator.next();
			
			entity = dataIterator.getValue();
			entity.update(container, game, delta);
		}
	}
}
