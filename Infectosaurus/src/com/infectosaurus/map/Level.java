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
public class Level {
	
	public Tile[][] tiles;
	public TileType[][] renderQueue;
	public static final int TILE_SIZE = 64;
	public static final int BLOCK_SIZE = TILE_SIZE/2;
	
	static final int NODE_DENSITY = 2;

	private static final Random rand = new Random();
	
	
	private int cameraPosX = 0;
	private int cameraPosY = 0;
	
	
	private Vector2Int mapSize;
	private Vector2Int[] pathNodes;




	public Level(){
		mapSize = new Vector2Int();
		loadTiles();
		generateRenderQueue();
		insertPathNodes();
	}

	private void generateRenderQueue() {
		renderQueue = new TileType[mapSize.x][mapSize.y];
		for (int i = 0; i < mapSize.x; i++) {
			for (int j = 0; j < mapSize.y; j++) {
				renderQueue[i][j] = tiles[i][j].tileType; 
			}
		}
	}

	private void loadTiles() {
		mapSize.x = 8;
		mapSize.y = 12;
		tiles = new Tile[mapSize.x][mapSize.y];
		for (int i = 0; i < mapSize.x; i++) {
			for (int j = 0; j < mapSize.y; j++) {
				tiles[i][j] = new Tile(BaseObject.gamePointers.tileSet.tileTypes[0]);
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

	
}
