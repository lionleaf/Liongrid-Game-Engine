package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.Vector2;

public class Collision {
	private static final int POINT = Shape.POINT;
	private static final int CIRCLE = Shape.CIRCLE;
	private static final int SQUARE = Shape.SQUARE;
	
	public static boolean collides(Vector2 point, Shape shape){
		if(shape.getShape() == CIRCLE){
			return collides(point, (Shape.Circle) shape);
		}
		return false;
	}
	
	public static boolean collides(Shape shape1, Shape shape2){
		
		if(shape1.getShape() == CIRCLE && shape2.getShape() == CIRCLE){
			return collides((Shape.Circle) shape1, 
							(Shape.Circle) shape2);
		}
		return false;
	}
	
	public static float distance2(Shape shape1, Shape shape2){
		if(shape1.getShape() == CIRCLE && shape2.getShape() == CIRCLE){
			return distance2((Shape.Circle) shape1, 
					(Shape.Circle) shape2);
		}
		return Float.NaN;
	}
	
	private static boolean collides(Shape.Circle circle1, Shape.Circle circle2){
		Vector2 pos1 = circle1.getPos();
		Vector2 pos2 = circle2.getPos();
		float radi = circle1.getRadius() + circle2.getRadius();
		
		if(pos1.distance2(pos2) < (radi)*(radi)) return true;
		return false;
	}
	
	private static boolean collides(Vector2 point, Shape.Circle circle){
		Vector2 pos1 = point;
		Vector2 pos2 = circle.getPos();
		float radius = circle.getRadius();
		if(pos1.distance2(pos2) < radius * radius) return true;
		return false;
	}
	
	private static float distance2(Shape.Circle circle1, Shape.Circle circle2){
		Vector2 pos1 = circle1.getPos();
		Vector2 pos2 = circle2.getPos();
		float r1 = circle1.getRadius();
		float r2 = circle2.getRadius();
		return pos1.distance2(pos2) - (r1+r2)*(r1+r2);
	}
}
