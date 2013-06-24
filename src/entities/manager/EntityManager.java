package entities.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

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
		data = new ArrayList<AbstractEntity>();
		dataHidden = new ArrayList<AbstractEntity>();
	}

	@Override
	protected void renderEntity(Graphics g, AbstractEntity entity) {
		if (entity.belongToRenderedAera()) {
			entity.draw(g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		Iterator<AbstractEntity> dataIterator = dataHidden.iterator();
		AbstractEntity entity;
		boolean boolHasToSort = false;
		
		HashMap<Integer, AbstractEntity> alreadyCheckedEntities = new HashMap<Integer, AbstractEntity>();
		
		while (dataIterator.hasNext()) {
			entity = dataIterator.next();
			if (entity.belongToRenderedAera()) {
				alreadyCheckedEntities.put(entity.hashCode(), entity);
				data.add(entity);
				dataIterator.remove();
			}
			entity.update(container, game, delta);
		}
		
		dataIterator = data.iterator();
		while (dataIterator.hasNext()) {
			entity = dataIterator.next();
			if (entity.update(container, game, delta)) {
				boolHasToSort = boolHasToSort == false ? true : true;
			}
			
			// si cette entité ne vient pas de la liste d'unités invisibles
			if (!alreadyCheckedEntities.containsKey(entity.hashCode())) {
				if (!entity.belongToRenderedAera()) {
					dataHidden.add(entity);
					dataIterator.remove();
					boolHasToSort = true;
				}
			}
		}
		
		if (boolHasToSort && !alreadyCheckedEntities.isEmpty()) {
			Collections.sort(data);
		}
	}
}
