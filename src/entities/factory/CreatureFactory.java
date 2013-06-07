package entities.factory;

import java.util.ArrayList;
import java.util.HashMap;

import entities.AbstractCreatureEntity;
import entities.types.creatures.CreatureJidiako;

/**
 * Se charge de gérer les unités creatures
 * @author co
 *
 */
public class CreatureFactory extends AbstractFactory<AbstractCreatureEntity> {
	
	// Creatures types
	public static final String CREATURE_JIDIAKO = "jidiako";
	
	
	protected static CreatureFactory instance;
	
	public static CreatureFactory getInstance() {
		if (instance == null) {
			instance = new CreatureFactory();
		}
		return instance;
	}
	
	protected CreatureFactory() {
		dataPool = new HashMap<String, ArrayList<AbstractCreatureEntity>>();
		dataPool.put("jidiako", new ArrayList<AbstractCreatureEntity>());
	}
	
	protected AbstractCreatureEntity getSpecificEntity(String entityType) {
		switch (entityType) {
		case "jidiako":
			return new CreatureJidiako();
		}
		
		return null;
	}
}
