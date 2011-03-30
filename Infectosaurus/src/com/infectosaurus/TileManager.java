package com.infectosaurus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class TileManager extends BaseObject {
	
	private Bitmap mBitmap;
	private int cameraPosX = 0;
	private int cameraPosY = 0;
	private static int TILE_SIZE = 64;
	private TileType[] tileTypes;
	Vector2 pos;
	DrawableBitmap drawBitmap;
	RenderSystem rSys;

	private Vector2 mapSize = new Vector2();
	int rcounter = 0;


	TileManager(){
		Panel panel = BaseObject.gamePointers.panel;
		initTileTypes();
		mBitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.scrub);
		drawBitmap = new DrawableBitmap(mBitmap, TILE_SIZE, TILE_SIZE);
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
		int X = tileX*TILE_SIZE - cameraPosX;
		int Y = tileY*TILE_SIZE - cameraPosY;
		int[] i = {X,Y};
		return i;

	}

	public int[] getTilePos(int x, int y){
		int tileX = ((cameraPosX + x)/TILE_SIZE);
		int tileY = ((cameraPosY + y)/TILE_SIZE);
		int[] rValue = {tileX, tileY};
		return rValue;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	public void clearArea(int x, int y, int width, int height) {
		int[] LowerLeft = getTilePos(x, y);
		int[] UpperRight = getTilePos(x + width, y + height);
		for(int tileX = LowerLeft[0]; tileX <= UpperRight[0]; tileX++){
			for(int tileY = LowerLeft[1]; tileY <= UpperRight[1]; tileY++){
				clearTile(tileX, tileY);
			}
		}
	}
	
	public void initTileTypes(){
		Panel panel = BaseObject.gamePointers.panel;
		Bitmap tile1 = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.scrub);
		
		Bitmap[] bitmaps = {tile1};
		tileTypes = new TileType[bitmaps.length];
		for(int i = 0; i < tileTypes.length; i++){
			tileTypes[i] = 
				new TileType(tile1, new boolean[2][2][MovementType.values().length], TILE_SIZE);
		}
	}
}
