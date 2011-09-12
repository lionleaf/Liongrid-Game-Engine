package mapeditor;

import com.liongrid.gameengine.tools.LVector2;

public abstract interface LShape {
	
	public static final int POINT  = 0;
	public static final int CIRCLE = 1;
	public static final int SQUARE = 2;
	
	/**
	 * @return the center position of the shape
	 */
	public LVector2 getPos();
	
	/**
	 * @return the integer that represents the shape. 
	 */
	public int getShape();
	
	public interface Circle extends LShape{
		/**
		 * @return the radius of the circle.
		 */
		public float getRadius();
	}
	
	public interface Square extends LShape{
		public float getWidth();
		public float getHeight();
		
	}
	
}
