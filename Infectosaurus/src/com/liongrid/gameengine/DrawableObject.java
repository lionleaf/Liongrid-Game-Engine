package com.liongrid.gameengine;

import javax.microedition.khronos.opengles.GL10;

public interface DrawableObject {
	
	/**
	 * Draws the object on the screen
	 * @param gl The GL10 that it will be drawn on
	 * @param x The x position
	 * @param y The y position
	 * @param scaleX The scale in X direction
	 * @param scaleY The scale in Y direction
	 */
	public void draw(GL10 gl, float x, float y, float scaleX, float scaleY);
	
	/**
	 * @return the current width (unscaled, but you should probably not scale)
	 */
	public int getWidth();
	
	/**
	 * @return the current height (unscaled, but you should probably not scale)
	 */
	public int getHeight();
	public boolean isCameraRelative();

	public Texture getTexture();
	
	//TODO This height and width does not depend upon the scale. 
	//Will introduce bugs at some point! FIX!
}
