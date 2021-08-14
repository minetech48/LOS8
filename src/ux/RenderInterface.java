package ux;

import engine.ux.GUI;
import engine.ux.GraphicsWrapper;
import engine.ux.UIElements.UIElement;

import java.awt.*;

public class RenderInterface extends UIElement {
	
	public RenderInterface(String[] args) {
		super(args);
	}
	
	public void draw() {
		GraphicsWrapper graphics;
		
		graphics = GUI.graphics;
		graphics.translate(getX(), getY());
		
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.setColor(Color.black);
		
		Renderer.render(graphics);
		
		graphics.translate(-getX(), -getY());
	}
}
