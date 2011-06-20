package com.liongrid.gameengine;

import android.util.Log;

import com.liongrid.gameengine.tools.Vector2;

public class Collision {
	private static final int CIRCLE = Collideable.CIRCLE;
	private static final int SQUARE = Collideable.SQUARE;
	public static boolean collides(Collideable shape1, Collideable shape2){
		
		if(shape1.getShape() == CIRCLE && shape2.getShape() == CIRCLE){
			return collides((Collideable.Circle) shape1, 
							(Collideable.Circle) shape2);
		}
		return false;
	}
	
	private static boolean collides(Collideable.Circle circle1, 
			Collideable.Circle circle2){
		
		Vector2 pos1 = circle1.getPos();
		Vector2 pos2 = circle2.getPos();
		float rad1 = circle1.getRadius();
		float rad2 = circle2.getRadius();
		
		if(pos1.distance2(pos2) < rad1 + rad2) return true;
		return false;
	}
}
