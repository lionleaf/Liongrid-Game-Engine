package com.infectosaurus.map;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

import android.content.res.AssetManager;
import android.util.Log;

import com.infectosaurus.Main;
import com.infectosaurus.R;
import com.infectosaurus.BaseObject;
import com.infectosaurus.GamePointers;
import com.infectosaurus.MovementType;
import com.infectosaurus.Panel;
import com.infectosaurus.Vector2Int;

public class Level {

	public Tile[][] tiles;
	public TileType[][] renderQueue;
	public static final int TILE_SIZE = 32;
	public static final int BLOCK_SIZE = TILE_SIZE/2;

	static final int NODE_DENSITY = 2;

	private static final Random rand = new Random();

	private Vector2Int mapSize;
	private Vector2Int[] pathNodes;

	
	/**
	 * @return the height in px
	 */
	public int getWidth(){
		return tiles.length * TILE_SIZE;
	}
	
	/**
	 * @return the height in px
	 */
	public int getHeight(){
		return tiles[0].length * TILE_SIZE;
	}
	
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
		loadTilesFromFile(R.raw.test);
	}

	private void generateTestTiles(){
		mapSize.x = 8;
		mapSize.y = 12;
		tiles = new Tile[mapSize.x][mapSize.y];
		for (int i = 0; i < mapSize.x; i++) {
			for (int j = 0; j < mapSize.y; j++) {
				tiles[i][j] = new Tile(BaseObject.gamePointers.tileSet.tileTypes[0]);
			}
		}
	}
	
	private void loadTilesFromFile(int res) {

		Panel panel = BaseObject.gamePointers.panel;
		InputStream inputStream = panel.getResources().openRawResource(res);

		AssetManager.AssetInputStream reader = (AssetManager.AssetInputStream) inputStream;

		TileType[] tileTypes = BaseObject.gamePointers.tileSet.tileTypes;

		//if(reader.nextInt() != 11) return;
		//byte[] workspaceBytes = new byte[4]; //Used to read ints

		try {
			//reader.read(workspaceBytes,0,4);

			//mapSize.x = byteArrayToInt(workspaceBytes);
			int id = reader.read(); //The version of the file
			mapSize.x = reader.read();
			mapSize.y = reader.read(); 
			
			
			Log.d(Main.TAG, mapSize.toString());
			tiles = new Tile[mapSize.x][mapSize.y];

			for (int i = 0; i < mapSize.x; i++) {
				for (int j = 0; j < mapSize.y; j++) {
					tiles[i][j] = new Tile(tileTypes[reader.read()]);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public final static int byteArrayToInt(byte[] b) {
		if (b.length != 4) {
			return 0;
		}

		// Same as DataInputStream's 'readInt' method
		/*int i = (((b[0] & 0xff) << 24) | ((b[1] & 0xff) << 16) | ((b[2] & 0xff) << 8)
                | (b[3] & 0xff));*/

		// little endian
		int i = (((b[3] & 0xff) << 24) | ((b[2] & 0xff) << 16) | ((b[1] & 0xff) << 8)
				| (b[0] & 0xff));

		return i;
	}

	private void insertPathNodes() {
		int count = mapSize.x * mapSize.y * NODE_DENSITY;
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
