package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.LVector2Int;

public class LCamera {
	
	private static float PIXELS_PER_WIDTH = 12*32;
	private static float PIXELS_PER_HEIGHT = 6*32;
	
	/**
	 * Position of the camera. 
	 */
	public static LVector2Int pos = new LVector2Int(0, 0);
	
	public static int screenHeight;
	public static int screenWidth;
	public static float scale;
	
	/**
	 * This function should be called to initialize the camera and make sure
	 * the screen height and width are set.
	 * 
	 * @param screenHeight - height of the screen in pixels
	 * @param screenWidth - width of the screen in pixels
	 */
	public static void init(int screenHeight, int screenWidth){
		LCamera.screenHeight = screenHeight;
		LCamera.screenWidth = screenWidth;
	}
    
	/**
     * Checks if the square is outside the camera view.
     * @param x - position x
     * @param y - position y
     * @param width - width
     * @param height - height
     * @return true if the whole square is outside the camera view
     */
    public static boolean cull(float x, float y, float width, float height){
    	if(x + width < LCamera.pos.x) return true;
    	if(x > LCamera.pos.x + LCamera.screenWidth/LCamera.scale) return true;
        if(y + height < LCamera.pos.y) return true;
    	if(y > LCamera.pos.y + LCamera.screenHeight/LCamera.scale) return true;
    	
    	return false;
    }
    
	/**
	 * This method sets the scale of the camera according to how many units should
	 * fit in the screen. This sets the scale relative to the width.
	 * @param pixels - how many pixels(bitmap) that should span the width of the screen.
	 */
	public static void setPixelsPerWidth(int pixels){
		LCamera.PIXELS_PER_WIDTH = pixels;
		LCamera.scale = LCamera.screenWidth/(LCamera.PIXELS_PER_WIDTH);
	}
	
	/**
	 * This method sets the scale of the camera according to how many units should
	 * fit in the screen. This sets the scale relative to the height.
	 * @param pixels - how many pixels(bitmap) that should span the height of the screen.
	 */
	public static void setPixelsPerHeight(int pixels){
		LCamera.PIXELS_PER_HEIGHT = pixels;
		LCamera.scale = LCamera.screenHeight/(LCamera.PIXELS_PER_HEIGHT);
	}
}
