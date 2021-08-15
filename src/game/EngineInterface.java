package game;

import engine.ux.GUI;
import engine.ux.KeyListener;
import ux.Renderer;

import java.util.HashSet;
import java.util.Set;

public class EngineInterface {
	protected static Set<String> keysDown = new HashSet<>();
	
	//init
	public static void initialize() {
		GUI.window.addKeyListener(new ListenerInterface());
		
		GameData.init();
	}
	
	//updating
	public static final int speed = 5;
	public static void update() {
		if (keysDown.contains("W"))
			GameData.playerPos.y-=speed;
		if (keysDown.contains("A"))
			GameData.playerPos.x-=speed;
		if (keysDown.contains("S"))
			GameData.playerPos.y+=speed;
		if (keysDown.contains("D"))
			GameData.playerPos.x+=speed;
	}
	
	//events
}

class ListenerInterface implements KeyListener {
	public void keyPressed(String keyText) {
		//key press handling
		switch (keyText) {
			case "J":
				Renderer.renderSegments = !Renderer.renderSegments;
				break;
			case "K":
				Renderer.renderRays = !Renderer.renderRays;
				break;
			case "L":
				Renderer.renderPoly = !Renderer.renderPoly;
				break;
		}
		
		//for player movement
		EngineInterface.keysDown.add(keyText);
	}
	
	public void keyReleased(String keyText) {
		EngineInterface.keysDown.remove(keyText);
	}
}
