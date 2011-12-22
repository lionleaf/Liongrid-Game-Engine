package com.liongrid.gameengine.tmx;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import com.liongrid.gameengine.LDrawableMap;

public class TMXTiledMapDrawable implements LDrawableMap{
	
	private TMXTiledMap mTMXTiledMap;
	private int rows;
	private int columns;
	private int offsetX;
	private int offestY;
	private Object[] layers;

	public TMXTiledMapDrawable(TMXTiledMap tiledMap) {
		mTMXTiledMap = tiledMap;
		rows = mTMXTiledMap.getTileRows();
		columns = mTMXTiledMap.getTileColumns();
		offsetX = (columns * mTMXTiledMap.getTileWidth())/2;
		layers = mTMXTiledMap.getTMXLayers().toArray();
		
	}

	@Override
	public void draw(GL10 gl, int cameraX, int cameraY, int cameraWidth,
			float cameraHeight, float scale) {
		for(Object o : layers){
		TMXLayer layer = (TMXLayer) o;
			layer.draw(gl, scale, scale, offsetX, 0);
		}
	}

}
