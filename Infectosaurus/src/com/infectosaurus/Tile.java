package com.infectosaurus;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Tile extends BaseObject {
	
	private Bitmap mBitmap;
	private int cameraPosX = 0;
	private int cameraPosY = 0;
	private static int tileSize = 64;
	Vector2 pos;
	DrawableBitmap drawBitmap;
	RenderSystem rSys;

	private Vector2 mapSize = new Vector2();
	int rcounter = 0;


	Tile(){
		Panel panel = BaseObject.gamePointers.panel;
		mBitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.scrub);
		drawBitmap = new DrawableBitmap(mBitmap, tileSize, tileSize);
		pos = new Vector2();
		rSys = BaseObject.gamePointers.renderSystem;
		mapSize.x = 10;
		mapSize.y = 10;
	}

	@Override
	public void update(float dt, BaseObject parent){
		clearTile(1,1);
	}

	public void clearTile(int tileX, int tileY){

		int[] i = getAbsTilePos(tileX, tileY);
		pos.x = i[0];
		pos.y = i[1];
		rSys.scheduleForDraw(drawBitmap, pos);
	}

	public void refreshMap(){
		for(int x = 0; x < mapSize.x ; x++){
			for(int y = 0 ; y < mapSize.y ; y++){
				clearTile(x,y);
			}
		}
	}


	private int[] getAbsTilePos(int x, int y) {
		int X = x*tileSize - cameraPosX;
		int Y = y*tileSize - cameraPosY;
		int[] i = {X,Y};
		return i;

	}

	public int[] getTile(int x, int y){
		int tileX = (cameraPosX + x)/tileSize;
		int tileY = (cameraPosY + y)/tileSize;
		int[] rValue = {tileX, tileY};
		return rValue;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}
}
