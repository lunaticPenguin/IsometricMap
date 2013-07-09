package entities.factory;

import java.util.ArrayList;
import java.util.HashMap;

import effects.AbstractEffect;
import effects.EffectNuke;

/**
 * Se charge de gérer les unités bâtiments
 * @author co
 *
 */
public class EffectFactory extends AbstractFactory<AbstractEffect> {
	
	// Creatures types
	public static final String EFFECT_NUKEBOMB = "EffectNuke";
	
	protected static EffectFactory instance;
	
	public static EffectFactory getInstance() {
		if (instance == null) {
			instance = new EffectFactory();
		}
		return instance;
	}
	
	protected EffectFactory() {
		dataPool = new HashMap<String, ArrayList<AbstractEffect>>();
		dataPool.put(EFFECT_NUKEBOMB, new ArrayList<AbstractEffect>());
	}
	
	protected AbstractEffect getSpecificEntity(String entityType) {
		switch (entityType) {

		case EFFECT_NUKEBOMB:
			return new EffectNuke();
		}
		
		return null;
	}
}
