package entities.factory;

import java.util.ArrayList;
import java.util.HashMap;

import entities.AbstractBuildingEntity;
import entities.types.buildings.TowerGuard;

/**
 * Se charge de gérer les unités bâtiments
 * @author co
 *
 */
public class BuildingFactory extends AbstractFactory<AbstractBuildingEntity> {
	
	// Creatures types
	public static final String CREATURE_GUARDTOWER = "jidiako";
	
	
	protected static BuildingFactory instance;
	
	public static BuildingFactory getInstance() {
		if (instance == null) {
			instance = new BuildingFactory();
		}
		return instance;
	}
	
	protected BuildingFactory() {
		dataPool = new HashMap<String, ArrayList<AbstractBuildingEntity>>();
		dataPool.put("towerguard", new ArrayList<AbstractBuildingEntity>());
	}
	
	protected AbstractBuildingEntity getSpecificEntity(String entityType) {
		switch (entityType) {
		case "towerguard":
			return new TowerGuard();
		}
		
		return null;
	}
}
