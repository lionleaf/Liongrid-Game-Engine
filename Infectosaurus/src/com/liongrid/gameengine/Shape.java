package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.Vector2;

public abstract interface Shape {
	
	public static final int CIRCLE = 1;
	public static final int SQUARE = 2;
	
	
	/**
	 * @return the center position of the shape
	 */
	public Vector2 getPos();
	
	/**
	 * @return the integer that represents the shape. 
	 */
	public int getShape();
	
	public interface Circle extends Shape{
		/**
		 * @return the radius of the circle.
		 */
		public float getRadius();
	}
	
	public interface Square extends Shape{
		
	}
	
	public interface CHCircle 
		extends Shape.CollisionHandler, Shape.Circle {
		
	}
	
	public interface CHSquare
		extends Shape.CollisionHandler, Shape.Square {
		
	}
	
	public abstract interface CollisionHandler extends Shape{
		/**
		 * Tells the object that it collides with a shape. 
		 * @param shape - The shape that the Collideable collides with.  
		 */
		public void collide(Shape shape);
		
		/**
		 * Erases old history and makes the collideable ready for new collisions.
		 * This is usually called before a set of .collides(shape) calls.
		 */
		public void clear();
		
		/**
		 * @return the index of the different types the object can collide with.
		 * returns null if the object can collide with every type.
		 */
		public int[] getPossibleCollisions();
		
		/**
		 * @return the index of the type(s) the object represents
		 */
		public int[] getType();
		
		public void expandHitbox(float dt);
		public void resetHitbox();
	}
}
