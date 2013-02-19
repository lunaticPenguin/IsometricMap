package gui;

import java.util.ArrayList;
import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.state.StateBasedGame;

import tools.Vector2i;

public abstract class Scene implements InputListener {
	
	protected Vector2i position;
	protected int width;
	protected int height;
	protected Scene parent; // pointeur vers parent
	
	protected Input input;
	protected ArrayList<Widget> widgets;
	
	public Scene(Vector2i position, int width, int height) {
		widgets = new ArrayList<Widget>();
		this.position = position;
		this.width = width;
		this.height = height;
	}
	
	public abstract void render(Graphics g);
	public abstract void init(GameContainer container, StateBasedGame game);
	
	
	protected void renderWidgets(Graphics g) {
		Iterator<Widget> it = widgets.iterator();
		while(it.hasNext()) {
			it.next().render(g);
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Vector2i getPosition() {
		return position;
	}
	
	public int getPositionX() {
		return position.x;
	}
	
	public int getPositionY() {
		return position.y;
	}
	
	public void setPosition(Vector2i pos) {
		position = pos;
	}
	
	public void setPositionX(int x) {
		position.x = x;
	}
	
	public void setPositionY(int y) {
		position.y = y;
	}
	
	public void addWidget(Widget widget) {
		widgets.add(widget);
		widget.setParent(this);
	}
	
	public void setParent(Scene parent) {
		this.parent = parent;
	}
	
	public Scene getParent() {
		return parent;
	}
	
	
	public boolean isColliding(int x, int y) {
		
		if (x < position.x + parent.getPositionX() || y < position.y + parent.getPositionY() || x > position.x + parent.getPositionX() + width || y > position.y + parent.getPositionY() + height) {
			return false;
		}
		return true;
	}
}
