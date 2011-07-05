package com.liongrid.infectosaurus.map;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import android.content.res.AssetManager;
import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Panel;
import com.liongrid.gameengine.tools.MovementType;
import com.liongrid.gameengine.tools.Vector2Int;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.Human;
import com.liongrid.infectosaurus.InfectoGameObjectHandler;
import com.liongrid.infectosaurus.R;

public class Level extends BaseObject{

	public Tile[][] tiles;
	public TileType[][] renderQueue;
	public static final int TILE_SIZE = 32;
	public static final int BLOCK_SIZE = TILE_SIZE/2;
	static final int NODE_DENSITY = 2;

	public static Vector2Int mapSizePx = new Vector2Int();
	public static Vector2Int mapSize;

	private static final Random rand = new Random();

	private Vector2Int[] pathNodes;
	
	private static final int defaultMap = R.raw.road2;

	
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
		//insertPathNodes();
	}

	private void generateRenderQueue() {
		renderQueue = new TileType[mapSize.x][mapSize.y];
		
		//Renderqueue must be inverted, since in OpenGL y=0 
		//is at the bottom of the screen
		
		for (int y = 0; y < mapSize.y; y++) {
			for (int x = 0; x < mapSize.x; x++) {
				renderQueue[x][y] = tiles[x][y].tileType; 
			}
		}
	}

	private void loadTiles() {
		loadTilesFromFile(defaultMap);
	}

	private void generateTestTiles(){
		mapSize.x = 8;
		mapSize.y = 12;
		tiles = new Tile[mapSize.x][mapSize.y];
		for (int i = 0; i < mapSize.x; i++) {
			for (int j = 0; j < mapSize.y; j++) {
				tiles[i][j] = new Tile(gamePointers.tileSet.tileTypes[0]);
			}
		}
	}
	
	private void loadTilesFromFile(int res) {

		Panel panel = gamePointers.panel;
		InputStream inputStream = panel.getResources().openRawResource(res);

		AssetManager.AssetInputStream reader = (AssetManager.AssetInputStream) inputStream;

		TileType[] tileTypes = gamePointers.tileSet.tileTypes;

		//if(reader.nextInt() != 11) return;
		//byte[] workspaceBytes = new byte[4]; //Used to read ints

		try {
			//reader.read(workspaceBytes,0,4);

			//mapSize.x = byteArrayToInt(workspaceBytes);
			int id = reader.read(); //The version of the file
			mapSize.x = reader.read();
			mapSizePx.x = mapSize.x * TILE_SIZE;
			mapSize.y = reader.read();
			mapSizePx.y = mapSize.y * TILE_SIZE;
			
			tiles = new Tile[mapSize.x][mapSize.y];

			int tileID;
			int index;
			for (int y = 0; y < mapSize.y; y++) {
				for (int x = 0; x < mapSize.x; x++) {
					tileID = reader.read(); 
					index = gamePointers.tileSet.tileIDtoIndexMap.get(tileID);
					
					//Fix the fucked up way the maps are saved. origo is saved as 
					//top-right, but here it`s bottom left...
					tiles[x][mapSize.y-y-1] = new Tile(tileTypes[index]);
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

				blocked = cTile.isBlocked(MovementType.Walk, 
						localX, 
						localY);
			}
		}
	}

	public boolean isPositionBlocked(int x, int y, MovementType mType){
		int xIndex = x/TILE_SIZE;
		int yIndex = y/TILE_SIZE;
		//outside the map is mBlocked for all!
		if(xIndex >= tiles.length || yIndex >= tiles[0].length 
				|| x < 0 || y < 0){
			return true;
		}
		
		//This should be safe now
		Tile tile = tiles[xIndex][yIndex];
		
		int localX = (x%TILE_SIZE)/BLOCK_SIZE;
		int localY = (y%TILE_SIZE)/BLOCK_SIZE;
		
		return tile.isBlocked(mType, localX, localY);
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void spawnNPCs(int nr){
		InfectoGameObjectHandler handler = GameActivity.infectoPointers.gameObjectHandler;
		for (int i = 0; i < nr; i++){
			Human newHuman = new Human();
			handler.add(newHuman);
			
			int width = gamePointers.level.getWidth();
			int height = gamePointers.level.getHeight();
			
			newHuman.pos.x = rand.nextInt(width);
			newHuman.pos.y = rand.nextInt(height); 
			
			Log.d("Infectosaurus","Human pos: "+newHuman.pos);
		}
	}
	
	@Override
	public void update(float dt, BaseObject parent) {
		// TODO Auto-generated method stub
		
	}
}
