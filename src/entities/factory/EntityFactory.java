package entities.factory;

import java.util.ArrayList;
import java.util.HashMap;

import entities.AbstractEntity;
import entities.types.buildings.TowerGuard;
import entities.types.creatures.CreatureBoloss;
import entities.types.creatures.CreatureJidiako;

/**
 * Se charge de gérer les unités bâtiments
 * @author co
 *
 */
public class EntityFactory extends AbstractFactory<AbstractEntity> {
	
	// Creatures types
	public static final String ENTITY_CREATUREJIDIAKO = "CreatureJidiako";
	public static final String ENTITY_CREATUREBOLOSS = "CreatureBoloss";
	public static final String ENTITY_TOWERGUARD = "TowerGuard";
	
	protected static EntityFactory instance;
	
	public static EntityFactory getInstance() {
		if (instance == null) {
			instance = new EntityFactory();
		}
		return instance;
	}
	
	protected EntityFactory() {
		dataPool = new HashMap<String, ArrayList<AbstractEntity>>();
		dataPool.put(ENTITY_CREATUREJIDIAKO, new ArrayList<AbstractEntity>());
		dataPool.put(ENTITY_CREATUREBOLOSS, new ArrayList<AbstractEntity>());
		dataPool.put(ENTITY_TOWERGUARD, new ArrayList<AbstractEntity>());
	}
	
	protected AbstractEntity getSpecificEntity(String entityType) {
		switch (entityType) {
		case ENTITY_TOWERGUARD:
			return new TowerGuard();
		case ENTITY_CREATUREJIDIAKO:
			return new CreatureJidiako();
		case ENTITY_CREATUREBOLOSS:
			return new CreatureBoloss();
		}
		return null;
	}
}
