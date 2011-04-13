package com.infectosaurus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class TileManager extends BaseObject {
	
	private static final boolean REDRAW_ALL = true;
	
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
		drawBitmap = new DrawableBitmap(
				R.drawable.scrub, TILE_SIZE, TILE_SIZE, panel.getContext());
		pos = new Vector2();
		rSys = BaseObject.gamePointers.renderSystem;
		mapSize.x = 8;
		mapSize.y = 12;
	}

	@Override
	public void update(float dt, BaseObject parent){
		if(REDRAW_ALL){
			refreshMap();
		}
	}

	public void clearTile(int x, int y){
		//We calculate the values here instead of calling a method for speed!
		rSys.scheduleForBGDraw(drawBitmap, 
				x*TILE_SIZE - cameraPosX, y*TILE_SIZE - cameraPosY);
	}

	public void refreshMap(){
		for(int x = 0; x < mapSize.x ; x++){
			for(int y = 0 ; y < mapSize.y ; y++){
				clearTile(x, y);
			}
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	public void clearArea(int x, int y, int width, int height) {
		if(REDRAW_ALL) return;
		
		//Most efficient way to do the math. Instead of making a method
		final int  lowerLeftX = ((cameraPosX + x)/TILE_SIZE);
		final int lowerLeftY = ((cameraPosY + y)/TILE_SIZE);
		final int upperRightX = ((cameraPosX + x + width)/TILE_SIZE);
		final int upperRightY = ((cameraPosY + y + height)/TILE_SIZE);
		
		for(int tileX = lowerLeftX; tileX <= upperRightX; tileX++){
			for(int tileY = lowerLeftY; tileY <= upperRightY; tileY++){
				clearTile(tileX, tileY);
			}
		}
	}
	
	public void initTileTypes(){
		Panel panel = BaseObject.gamePointers.panel;
		int tile1 = R.drawable.scrub;
		
		int[] bitmaps = {tile1};
		tileTypes = new TileType[bitmaps.length];
		for(int i = 0; i < tileTypes.length; i++){
			tileTypes[i] = 
				new TileType(tile1, new boolean[2][2][MovementType.values().length], 
						TILE_SIZE, panel.getContext());
		}
	}
}
