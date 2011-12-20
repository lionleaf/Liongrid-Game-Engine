package com.liongrid.gameengine;

import javax.microedition.khronos.opengles.GL10;

public interface LDrawableMap {
	public void draw(GL10 gl, int cameraX, int cameraY, int cameraWidth, float cameraHeight, float scale);

}
