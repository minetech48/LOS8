package game;

public class WallEndpoint implements Comparable<WallEndpoint> {
	public float x, y;
	public WallEndpoint pair;
	
	public WallEndpoint(float x1, float y1, float x2, float y2) {
		x = x1;
		y = y1;
		
		pair = new WallEndpoint(x2, y2, this);
	}
	public WallEndpoint(float x, float y, WallEndpoint pair) {
		this.x = x;
		this.y = y;
		
		this.pair = pair;
		GameData.wallEndpoints.add(this);
	}
	
	//gets angle from player to point
	public float getPlayerAngle() {
		return (float) Math.atan2(y-GameData.playerPos.y, x-GameData.playerPos.x);
	}
	
	//compares based on angle to player
	public int compareTo(WallEndpoint other) {
		return (int) ((getPlayerAngle()-other.getPlayerAngle())*1000);
	}
}
