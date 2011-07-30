package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.Vector2;

public abstract interface Shape {
	
	public static final int POINT  = 0;
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
	
}
