package main;

import engine.core.EventBus;
import engine.util.Johnson;
import engine.ux.GUI;
import ux.RenderInterface;

public class Main {
	public static void main(String[] args) {
		EventBus.initializeHandler(GUI.class);
		
		
		
		Johnson.addDeserializableClass("RenderInterface", RenderInterface.class.getName());
		
		EventBus.start();
	}
}
