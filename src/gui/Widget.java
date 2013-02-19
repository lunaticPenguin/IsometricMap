package gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.InputListener;

import tools.Vector2i;

public abstract class Widget extends Scene {
	
	public Widget(Vector2i position, int width, int height) {
		super(position, width, height);
	}
	public abstract void render(Graphics g);
	protected final void renderWidgets(Graphics g) {}
}
