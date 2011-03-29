package com.infectosaurus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class LevelBuilder extends BaseObject {
	
	private Bitmap mBitmap;
	private int cameraPosX = 0;
	private int cameraPosY = 0;
	private static int tileSize = 64;
	Vector2 pos;
	DrawableBitmap drawBitmap;
	RenderSystem rSys;

	private Vector2 mapSize = new Vector2();
	int rcounter = 0;


	LevelBuilder(){
		Panel panel = BaseObject.gamePointers.panel;
		mBitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.scrub);
		drawBitmap = new DrawableBitmap(mBitmap, tileSize, tileSize);
		pos = new Vector2();
		rSys = BaseObject.gamePointers.renderSystem;
		mapSize.x = 8;
		mapSize.y = 12;
	}

	@Override
	public void update(float dt, BaseObject parent){
//		refreshMap();
	}

	public void clearTile(int x, int y){
		int[] i = getAbsTilePos(x, y);
		rSys.scheduleForBGDraw(drawBitmap, i[0], i[1]);
	}

	public void refreshMap(){
		for(int x = 0; x < mapSize.x ; x++){
			for(int y = 0 ; y < mapSize.y ; y++){
				clearTile(x, y);
			}
		}
	}


	private int[] getAbsTilePos(int tileX, int tileY) {
		int X = tileX*tileSize - cameraPosX;
		int Y = tileY*tileSize - cameraPosY;
		int[] i = {X,Y};
		return i;

	}

	public int[] getTile(int x, int y){
		int tileX = ((cameraPosX + x)/tileSize);
		int tileY = ((cameraPosY + y)/tileSize);
		int[] rValue = {tileX, tileY};
		return rValue;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	public void clearArea(int x, int y, int width, int height) {
		int[] LowerLeft = getTile(x, y);
		int[] UpperRight = getTile(x + width, y + height);
		for(int tileX = LowerLeft[0]; tileX <= UpperRight[0]; tileX++){
			for(int tileY = LowerLeft[1]; tileY <= UpperRight[1]; tileY++){
				clearTile(tileX, tileY);
			}
		}
	}
}
