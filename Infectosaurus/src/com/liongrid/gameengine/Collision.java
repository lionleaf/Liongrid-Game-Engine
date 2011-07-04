package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.Vector2;

public class Collision {
	private static final int CIRCLE = Shape.CIRCLE;
	private static final int SQUARE = Shape.SQUARE;
	public static boolean collides(Shape shape1, Shape shape2){
		
		if(shape1.getShape() == CIRCLE && shape2.getShape() == CIRCLE){
			return collides((Shape.Circle) shape1, 
							(Shape.Circle) shape2);
		}
		return false;
	}
	
	private static boolean collides(Shape.Circle circle1, 
			Shape.Circle circle2){
		Vector2 pos1 = circle1.getPos();
		Vector2 pos2 = circle2.getPos();
		float distance = circle1.getRadius() + circle2.getRadius();;
		
		if(pos1.distance2(pos2) < (distance)*(distance)) return true;
		return false;
	}
}
