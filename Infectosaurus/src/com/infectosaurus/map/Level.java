package com.infectosaurus.map;

import java.util.Random;

import com.infectosaurus.BaseObject;
import com.infectosaurus.DrawableBitmap;
import com.infectosaurus.MovementType;
import com.infectosaurus.Panel;
import com.infectosaurus.R;
import com.infectosaurus.RenderSystem;
import com.infectosaurus.Vector2;
import com.infectosaurus.R.drawable;
import com.infectosaurus.Vector2Int;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 
 *
 */
public class Level extends BaseObject {
	
	static final boolean REDRAW_ALL = true;
	static final int NODE_DENSITY = 2;
	static final int TILE_SIZE = 64;
	static final int BLOCK_SIZE = TILE_SIZE/2;
	static Random rand = new Random();
	
	private Bitmap mBitmap;
	private int cameraPosX = 0;
	private int cameraPosY = 0;
	private Tile[][] tiles;
	private Vector2Int mapSize;
	private Vector2Int[] pathNodes;
	Vector2 pos;
	DrawableBitmap drawBitmap;
	RenderSystem rSys;
	
	int rcounter = 0;


	public Level(){
		Panel panel = BaseObject.gamePointers.panel;
		drawBitmap = new DrawableBitmap(
				R.drawable.scrub, TILE_SIZE, TILE_SIZE, panel.getContext());
		pos = new Vector2();
		rSys = BaseObject.gamePointers.renderSystem;
		mapSize = new Vector2Int();
		loadTiles();
		insertPathNodes();
	}

	private void loadTiles() {
		// TODO Auto-generated method stub
		mapSize.x = 8;
		mapSize.y = 12;
		tiles = new Tile[mapSize.x][mapSize.y];
		for (int i = 0; i < mapSize.x; i++) {
			for (int j = 0; j < mapSize.y; j++) {
				tiles[i][j] = new Tile();
				tiles[i][j].tileType = gamePointers.tileSet.tileTypes[0];
				
			}
		}
	}

	private void insertPathNodes() {
		int count = mapSize.x * mapSize.y* NODE_DENSITY;
		pathNodes = new Vector2Int[count];
		boolean blocked = true;
		for(int i = 0; i < count; i++){
			while(blocked){
				pathNodes[i] = new Vector2Int(rand.nextInt(mapSize.x),
										      rand.nextInt(mapSize.y));
				
				
				Tile cTile = 
					tiles[pathNodes[i].x/TILE_SIZE][pathNodes[i].y/TILE_SIZE];
				
				int localX = (pathNodes[i].x%TILE_SIZE)/BLOCK_SIZE;
				int localY = (pathNodes[i].y%TILE_SIZE)/BLOCK_SIZE;
				
				blocked = cTile.isBlocked(MovementType.WALKING, 
						            localX, 
						            localY);
			}
		}
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
	
}
