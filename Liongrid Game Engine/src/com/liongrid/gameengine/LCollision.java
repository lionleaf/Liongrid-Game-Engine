package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.LVector2;

public class LCollision {
	private static final int CIRCLE = LShape.CIRCLE;
	private static final int SQUARE = LShape.SQUARE;
	private static final int NON_ROTATE_SQUARE = LShape.NON_ROTATE_SQUARE;
	
	/**
	 * Checks if a shape collides with a point.
	 * @param point
	 * @param shape
	 * @return true if collides. False if the shape is not supported.
	 */
	public static boolean collides(LVector2 point, LShape shape){
		if(shape.getShape() == CIRCLE){
			return collides(point.x, point.y, (LShape.Circle) shape);
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
	public static boolean collides(float x, float y, LShape shape){
		if(shape.getShape() == CIRCLE){
			return collides(x, y, (LShape.Circle) shape);
		}
		return false;
	}
	
	/**
	 * Checks if two shapes collide. Returns false if shape not supported.
	 * @param shape1
	 * @param shape2
	 * @return true if collides. False if the shapes are not supported
	 */
	public static boolean collides(LShape shape1, LShape shape2){
		if(shape1.getShape() == CIRCLE && shape2.getShape() == CIRCLE){
			return collides((LShape.Circle) shape1, 
							(LShape.Circle) shape2);
		}
		if(shape1.getShape() == NON_ROTATE_SQUARE && shape2.getShape() == NON_ROTATE_SQUARE){
			return collides((LShape.NonRotateSquare) shape1, 
							(LShape.NonRotateSquare) shape2);
		}
		return false;
	}
	
	
	private static boolean collides(LShape.NonRotateSquare square1, 
			LShape.NonRotateSquare square2){
		
		LVector2 pos1 = square1.getPos();
		LVector2 pos2 = square2.getPos();
		float width = square1.getWidth() + square2.getWidth();
		width = width / 2f; //Max distance between centers in y.
		
		float height = square1.getHeight() + square2.getHeight();
		height = height / 2f;
		
		if(Math.abs(pos1.x - pos2.x) < width && Math.abs(pos1.y - pos2.y) < height) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the closest distance between two shapes. The distance is squared.
	 * If no specific shape is found, returns the distance between the two points.
	 * @param shape1
	 * @param shape2
	 * @return distance squared
	 */
	public static float distance2(LShape shape1, LShape shape2){
		if(shape1.getShape() == CIRCLE && shape2.getShape() == CIRCLE){
			return distance2((LShape.Circle) shape1, (LShape.Circle) shape2);
		}
		else{
			LVector2 pos1 = shape1.getPos();
			LVector2 pos2 = shape2.getPos();
			return (pos2.x - pos1.x)*(pos2.x - pos1.x) + (pos2.y - pos1.y)*(pos2.y - pos1.y);
		}
	}
	
	private static boolean collides(LShape.Circle circle1, LShape.Circle circle2){
		LVector2 pos1 = circle1.getPos();
		LVector2 pos2 = circle2.getPos();
		float radi = circle1.getRadius() + circle2.getRadius();
		
		if(pos1.distance2(pos2) < (radi)*(radi)) return true;
		return false;
	}
	
	private static boolean collides(float x, float y, LShape.Circle circle){
		LVector2 pos = circle.getPos();
		float distance2 = pos.distance2(x, y);
		float radius = circle.getRadius();
		if(distance2 < radius * radius) return true;
		return false;
	}
	
	private static float distance2(LShape.Circle circle1, LShape.Circle circle2){
		LVector2 pos1 = circle1.getPos();
		LVector2 pos2 = circle2.getPos();
		float r1 = circle1.getRadius();
		float r2 = circle2.getRadius();
		return pos1.distance2(pos2) - (r1+r2)*(r1+r2);
	}
}
