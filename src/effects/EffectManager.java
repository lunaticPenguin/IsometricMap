package effects;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import entities.factory.EffectFactory;
import entities.manager.AbstractManager;

public class EffectManager extends AbstractManager<AbstractEffect> {
	
	protected static EffectManager instance;
	
	public static EffectManager getInstance() {
		if (instance == null) {
			instance = new EffectManager();
		}
		return instance;
	}
	
	protected EffectManager() {
		factory = EffectFactory.getInstance();
		data = new ArrayList<AbstractEffect>();
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta, Graphics g) {
		
	}
	
	public void render(Graphics g) {
		
		Iterator<AbstractEffect> dataIterator = data.iterator();
		AbstractEffect tmpEffect;
		
		while (dataIterator.hasNext()) {
			tmpEffect = dataIterator.next();
			if (tmpEffect.isFinished()) {
				tmpEffect.stop();
				dataIterator.remove();
				factory.setEntityBack(tmpEffect.getClass().getSimpleName(), tmpEffect);
			} else {
				this.renderEntity(g, tmpEffect);
			}
		}
	}
	
	@Override
	protected void renderEntity(Graphics g, AbstractEffect entity) {
		
		if (entity.isPlaying()) {
			entity.render(g);
		}
	}

}
