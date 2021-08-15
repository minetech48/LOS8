package ux;

import engine.util.vector.Vec2f;
import engine.ux.GraphicsWrapper;
import game.GameData;
import game.WallEndpoint;

import javax.xml.ws.Endpoint;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Renderer {
	
	public static boolean renderSegments = true, renderRays = true, renderPoly;
	
	public static void render(GraphicsWrapper graphics) {
		if (!GameData.initialized) return;
		
		Set<WallEndpoint> drawnPoints = new HashSet<>();
		
		GameData.sortEndpoints();
		
		//ray-casting
		Set<WallEndpoint> intersectableSegments = new HashSet<>();
		ArrayList<Point> resultPolygon = new ArrayList<>();
		
		float angle;
		double dist, temp, dist2;
		
		for (WallEndpoint endpoint : GameData.wallEndpoints) {
			
			//adding/ removing intersectableSegments
			if (intersectableSegments.contains(endpoint.pair)) {
				intersectableSegments.remove(endpoint.pair);
			} else {
				intersectableSegments.add(endpoint);
			}
			
			dist2 = -1;
			angle = endpoint.getPlayerAngle();
			for (int i = -1; i < 2; i+=2) {
				dist = 1000;
				
				for (WallEndpoint segment : GameData.wallEndpoints) {
					temp = rayCast(GameData.playerPos, -angle + i*0.001f, segment);
					
					if (temp <= dist)
						dist = temp;
				}
				
				if (dist > dist2)
					dist2 = dist;
			}
			
			resultPolygon.add(new Point(
					(int) (GameData.playerPos.x + Math.cos(angle) * dist2),
					(int) (GameData.playerPos.y + Math.sin(angle) * dist2)));
		}
		
		//drawing walls
		if (renderSegments) {
			for (WallEndpoint endpoint : GameData.wallEndpoints) {
				if (drawnPoints.contains(endpoint)) continue;
				
				drawnPoints.add(endpoint.pair);
				
				graphics.drawLine((int) endpoint.x, (int) endpoint.y, (int) endpoint.pair.x, (int) endpoint.pair.y);
			}
		}
		
		//drawing ray-casted polygon
		if (renderPoly) {
			for (int i = 0; i < resultPolygon.size() - 1; i++) {
				graphics.drawLine(
						resultPolygon.get(i).x,
						resultPolygon.get(i).y,
						resultPolygon.get(i + 1).x,
						resultPolygon.get(i + 1).y);
			}
			int i = resultPolygon.size() - 1;
			graphics.drawLine(
					resultPolygon.get(i).x,
					resultPolygon.get(i).y,
					resultPolygon.get(0).x,
					resultPolygon.get(0).y);
		}
		
		//drawing rays
		if (renderRays) {
			for (Point p : resultPolygon) {
				graphics.drawLine(
						(int) GameData.playerPos.x,
						(int) GameData.playerPos.y,
						p.x, p.y);
			}
		}
		
		//drawing player
		graphics.getGraphics().fillOval(
				(int) GameData.playerPos.x-5,
				(int) GameData.playerPos.y-5,
				10, 10);
	}
	
	public static double rayCast(Vec2f startPoint, float angle, WallEndpoint segment) {
		double segX1p = Math.cos(angle)*(segment.x - startPoint.x) + -Math.sin(angle)*(segment.y - startPoint.y);/*Matrix rotation of point 1*/
		double segY1p = Math.sin(angle)*(segment.x - startPoint.x) +  Math.cos(angle)*(segment.y - startPoint.y);
		double segX2p = Math.cos(angle)*(segment.pair.x - startPoint.x) + -Math.sin(angle)*(segment.pair.y - startPoint.y);/*Matrix rotation of point 2*/
		double segY2p = Math.sin(angle)*(segment.pair.x - startPoint.x) +  Math.cos(angle)*(segment.pair.y - startPoint.y);
		
		if (segY1p*segY2p > 0) {
			return 1000;
		}
		
		double dist = -(segY1p * (segX1p - segX2p) /
						(segY1p - segY2p) - segX1p);
		
		if (dist < 1 || Double.isInfinite(dist)) {
			return 1000;
		}else{
			return dist;
		}
	}
}
