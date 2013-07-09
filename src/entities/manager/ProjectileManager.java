package entities.manager;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import entities.factory.ProjectileFactory;
import entities.types.projectiles.AbstractProjectile;

/**
 * Le manager de projectile contient bien 2 listes : une affichée, une masquée
 * mais n'utilise que celle affichée
 * @author co
 *
 */
public class ProjectileManager extends AbstractManager<AbstractProjectile> {
	
	protected static ProjectileManager instance;
	
	public static ProjectileManager getInstance() {
		if (instance == null) {
			instance = new ProjectileManager();
		}
		return instance;
	}
	
	protected ProjectileManager() {
		factory = ProjectileFactory.getInstance();
		data = new ArrayList<AbstractProjectile>();
	}

	@Override
	protected void renderEntity(Graphics g, AbstractProjectile projectile) {
		if (projectile.belongToRenderedAera()) {
			projectile.draw(g);
		}
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta, Graphics g) {
		
		Iterator<AbstractProjectile> dataIterator = data.iterator();
		AbstractProjectile projectile;
		
		dataIterator = data.iterator();
		while (dataIterator.hasNext()) {
			projectile = dataIterator.next();
			
			// si projectile détruit
			if (!projectile.update(container, game, delta)) {
				dataIterator.remove();
				factory.setEntityBack(projectile.getClass().getSimpleName(), projectile);
			} else {
				this.renderEntity(g, projectile);
			}
		}
	}
}
