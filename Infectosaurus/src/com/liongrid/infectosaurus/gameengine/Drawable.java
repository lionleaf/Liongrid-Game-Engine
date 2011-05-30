package com.liongrid.infectosaurus.gameengine;

import javax.microedition.khronos.opengles.GL10;

public interface Drawable {
	
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
	 * @return the width that will be drawn if you call draw now
	 */
	public int getWidth();
	
	/**
	 * @return the width that will be drawn if you call draw now
	 */
	public int getHeight();
	
	//TODO This height and width does not depend upon the scale. 
	//Will introduce bugs at some point! FIX!
}
