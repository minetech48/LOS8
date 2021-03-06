package game;

import engine.util.vector.Vec2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class GameData {
	
	public static boolean initialized = false;
	
	public static Vec2f playerPos;
	
	public static boolean arrayIsSorted = false;
	public static ArrayList<WallEndpoint> wallEndpoints;
	
	public static void init() {
		playerPos = new Vec2f();
		
		wallEndpoints = new ArrayList<WallEndpoint>() {
			@Override
			public boolean add(WallEndpoint o) {
				arrayIsSorted = false;
				
				return super.add(o);
			}
		};
		
		addPolygon(400, 400, 6, 50);
		
		//initializing environment
		wallEndpoints.add(new WallEndpoint(0, 0, 800, 0));
		wallEndpoints.add(new WallEndpoint(800, 0, 800, 800));
		wallEndpoints.add(new WallEndpoint(800, 800, 0, 800));
		wallEndpoints.add(new WallEndpoint(0, 800, 0, 0));
		
		wallEndpoints.add(new WallEndpoint(100, 100, 200, 150));
		wallEndpoints.add(new WallEndpoint(300, 40, 350, 10));
		wallEndpoints.add(new WallEndpoint(50, 200, 100, 100));
		wallEndpoints.add(new WallEndpoint(200, 200, 100, 150));
		
		initialized = true;
	}
	
	public static void addPolygon(float x, float y, int sides, int size) {
		double increment = Math.PI*2/sides;
		
		double angle;
		
		for (int i = 0; i < sides; i++) {
			angle = i*increment;
			wallEndpoints.add(new WallEndpoint((float) Math.cos(angle)*size + x, (float) Math.sin(angle)*size + y, (float) Math.cos(angle+increment)*size + x, (float) Math.sin(angle+increment)*size + y));
		}
	}
	
	public static void sortEndpoints() {
		if (arrayIsSorted) return;
		
		wallEndpoints.sort(new Comparator<WallEndpoint>() {
			@Override
			public int compare(WallEndpoint o1, WallEndpoint o2) {
				return o1.compareTo(o2);
			}
		});
	}
}
