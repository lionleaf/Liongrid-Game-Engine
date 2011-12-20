package com.liongrid.gameengine.tmx;

import javax.microedition.khronos.opengles.GL10;

import com.liongrid.gameengine.LDrawableMap;

public class TMXTiledMapDrawable implements LDrawableMap{
	
	private TMXTiledMap mTMXTiledMap;

	public TMXTiledMapDrawable(TMXTiledMap tiledMap) {
		mTMXTiledMap = tiledMap;
	}

	@Override
	public void draw(GL10 gl, int cameraX, int cameraY, int cameraWidth,
			float cameraHeight, float scale) {
		
	}

}
