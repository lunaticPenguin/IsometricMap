package entities.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

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
		data = new ArrayList<AbstractEntity>();
	}

	@Override
	protected void renderEntity(Graphics g, Camera cam, AbstractEntity entity) {
		if (entity.belongToRenderedAera(cam)) {
			entity.draw(g, cam);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		Iterator<AbstractEntity> dataIterator = data.iterator();
		boolean boolHasToSort = false;
		while (dataIterator.hasNext()) {
			if (dataIterator.next().update(container, game, delta) && !boolHasToSort) {
				boolHasToSort = true;
			}
		}
		
		if (boolHasToSort) {
			Collections.sort(data);
		}
	}
}
