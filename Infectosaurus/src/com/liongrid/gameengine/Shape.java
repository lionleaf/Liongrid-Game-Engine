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
	
	public interface CHCircle<T extends Shape> 
		extends Shape.CollisionHandler<T>, Shape.Circle {
		
	}
	
	public interface CHSquare<T extends Shape>
		extends Shape.CollisionHandler<T>, Shape.Square {
		
	}
	
	public abstract interface CollisionHandler<T extends Shape> extends Shape{
		/**
		 * Tells the object that it collides with a shape. 
		 * @param object - The shape that the Collideable collides with.  
		 */
		public void collide(T object);
		
		/**
		 * Erases old history and makes the collideable ready for new collisions.
		 * This is usually called before a set of .collides(shape) calls.
		 */
		public void clear();
		
		/**
		 * @return the index of the type the object represents
		 */
		public int getType();
		
		public void expandHitbox(float dt);
		public void resetHitbox();
	}
}
