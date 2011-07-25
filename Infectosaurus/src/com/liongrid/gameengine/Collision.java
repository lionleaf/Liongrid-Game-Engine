package com.liongrid.gameengine;

import android.util.Log;

import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.Main;

public class Collision {
	private static final int CIRCLE = Shape.CIRCLE;
	private static final int SQUARE = Shape.SQUARE;
	
	/**
	 * Checks if a shape collides with a point.
	 * @param point
	 * @param shape
	 * @return true if collides. False if the shape is not supported.
	 */
	public static boolean collides(Vector2 point, Shape shape){
		if(shape.getShape() == CIRCLE){
			return collides(point.x, point.y, (Shape.Circle) shape);
		}
		return false;
	}
	
	/**
	 * Checks if a shape collides with a point.
	 * @param x
	 * @param y
	 * @param shape
	 * @return true if collides. False if the shape is not supported.
	 */
	public static boolean collides(float x, float y, Shape shape){
		if(shape.getShape() == CIRCLE){
			return collides(x, y, (Shape.Circle) shape);
		}
		return false;
	}
	
	/**
	 * Checks if two shapes collide. Returns false if shape not supported.
	 * @param shape1
	 * @param shape2
	 * @return true if collides. False if the shapes are not supported
	 */
	public static boolean collides(Shape shape1, Shape shape2){
		if(shape1.getShape() == CIRCLE && shape2.getShape() == CIRCLE){
			return collides((Shape.Circle) shape1, 
							(Shape.Circle) shape2);
		}
		return false;
	}
	
	/**
	 * Returns the closest distance between two shapes. The distance is squared.
	 * @param shape1
	 * @param shape2
	 * @return distance squared
	 */
	public static float distance2(Shape shape1, Shape shape2){
		if(shape1.getShape() == CIRCLE && shape2.getShape() == CIRCLE){
			return distance2((Shape.Circle) shape1, (Shape.Circle) shape2);
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
	
	private static boolean collides(float x, float y, Shape.Circle circle){
		Vector2 pos = circle.getPos();
		float distance2 = pos.distance2(x, y);
		float radius = circle.getRadius();
		if(distance2 < radius * radius) return true;
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
