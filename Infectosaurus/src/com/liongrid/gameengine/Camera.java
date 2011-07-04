package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.Vector2Int;

public class Camera {
	
	/**
	 * Default units per width is 12. 
	 */
	public static float UNITS_PER_WIDTH = 12;
	
	/**
	 * Dafault units per height is 6.
	 */
	public static float UNITS_PER_HEIGHT = 6;
	
	/**
	 * Position of the camera in in-game coordinates. 
	 */
	public static Vector2Int pos = new Vector2Int(0, 0);
	
	/**
	 * A unit that should be used as an universal in-game unit. This is usually
	 * a number of unscaled pixels. For instance, one could say that 10 
	 * pixels represents one meter in-game, and that the width of a person is
	 * 0.5 units. 
	 */
	public static int unit;
	
	public static int screenHeight;
	public static int screenWidth;
	public static float scale;
	
	/**
	 * This function should be called to initialize the camera and make sure
	 * the screen height and width are set. An in game "unit" is also set.
	 * 
	 * @param screenHeight - height of the screen in pixels
	 * @param screenWidth - width of the screen in pixels
	 * @param unit - in game unit in in-game coordinates (unscaled pixels). This 
	 * should be used as an universal unit. For instance, one could say that 10 
	 * pixels represents one meter in-game, and that the width of a person is
	 * 0.5 units. 
	 */
	public static void init(int screenHeight, int screenWidth, int unit){
		Camera.screenHeight = screenHeight;
		Camera.screenWidth = screenWidth;
		Camera.unit = unit;
	}
	
	/**
	 * This method sets the scale of the camera according to how many units should
	 * fit in the screen. This sets the scale relative to the width.
	 * @param units - how many units that should span the width of the screen.
	 */
	public static void setUnitsPerWidth(int units){
		Camera.UNITS_PER_WIDTH = units;
		Camera.scale = 
			Camera.screenWidth/(Camera.UNITS_PER_WIDTH*Camera.unit);
	}
	
	/**
	 * This method sets the scale of the camera according to how many units should
	 * fit in the screen. This sets the scale relative to the height.
	 * @param units - how many units that should span the height of the screen.
	 */
	public static void setUnitsPerHeight(int units){
		Camera.UNITS_PER_HEIGHT = units;
		Camera.scale = 
			Camera.screenHeight/(Camera.UNITS_PER_HEIGHT*Camera.unit);
	}
}
