package entities.manager;

import java.util.ArrayList;
import java.util.HashMap;

import entities.AbstractCreatureEntity;

public class CreatureManager extends AbstractManager<AbstractCreatureEntity> {
	
	protected static CreatureManager instance;
	
	public static CreatureManager getInstance() {
		if (instance == null) {
			instance = new CreatureManager();
		}
		return instance;
	}
	
	protected CreatureManager() {
		data = new HashMap<String, ArrayList<AbstractCreatureEntity>>();
	}
	
	
}
