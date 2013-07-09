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
	
	protected HashMap<String, HashMap<String, Integer>> enemiesMapping;
	
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
		
		enemiesMapping = new HashMap<String, HashMap<String, Integer>>();
		enemiesMapping.put("CreatureJidiako", new HashMap<String, Integer>());
		enemiesMapping.get("CreatureJidiako").put("TowerGuard", 1);
		enemiesMapping.get("CreatureJidiako").put("CreatureJidiako", 0);
		
		enemiesMapping.put("TowerGuard", new HashMap<String, Integer>());
		enemiesMapping.get("TowerGuard").put("CreatureJidiako", 1);
		enemiesMapping.get("TowerGuard").put("TowerGuard", 0);
	}
	
	/**
	 * Permet de savoir si 2 entités sont hostiles entre elles
	 * @param entityA
	 * @param entityB
	 * @return
	 */
	protected boolean areEntitiesHostile(AbstractEntity entityA, AbstractEntity entityB) {
		if (enemiesMapping.containsKey(entityA.getClass().getSimpleName())) {
			if (enemiesMapping.get(entityA.getClass().getSimpleName()).containsKey(entityB.getClass().getSimpleName())) {
				return enemiesMapping.get(entityA.getClass().getSimpleName()).get(entityB.getClass().getSimpleName()) == 1;
			}
		}
		return false;
	}

	@Override
	protected void renderEntity(Graphics g, AbstractEntity entity) {
		if (entity.belongToRenderedAera()) {
			entity.draw(g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta, Graphics g) {

		AbstractEntity entity, secondEntity;
		boolean boolHasToSort = false;

		Iterator<AbstractEntity> dataIterator = data.iterator();
		Iterator<AbstractEntity> dataSecondIterator;
		
		while (dataIterator.hasNext()) {
			
			entity = dataIterator.next();
			dataSecondIterator = data.iterator();
			
			if (entity.update(container, game, delta)) {
				boolHasToSort = boolHasToSort == false ? true : true;
			}
			
			while (dataSecondIterator.hasNext()) {
				
				secondEntity = dataSecondIterator.next();
				
				if (entity != secondEntity) {
					if (entity.isEntityInActionZone(secondEntity)) {
						if (areEntitiesHostile(entity, secondEntity)) {
							entity.assignTarget(secondEntity);
						}
					}
				}
			}
			
			if (entity.isDead()) {
				dataIterator.remove();
				factory.setEntityBack(entity.getClass().getSimpleName(), entity);
			} else {
				this.renderEntity(g, entity);
			}
		}
		// tri des entités
		Collections.sort(data);
	}
}
