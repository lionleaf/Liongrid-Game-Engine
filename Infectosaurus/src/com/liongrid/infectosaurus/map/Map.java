package com.liongrid.infectosaurus.map;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import android.content.res.AssetManager;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LSurfaceViewPanel;
import com.liongrid.gameengine.tools.LMovementType;
import com.liongrid.gameengine.tools.LVector2Int;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.InfectoGameObjectHandler;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.SpawnPool;

public class Map extends LBaseObject{

	public Tile[][] tiles;
	public TileType[][] renderQueue;
	public static final int TILE_SIZE = 32;
	public static final int BLOCK_SIZE = TILE_SIZE/2;
	static final int NODE_DENSITY = 2;

	public static LVector2Int sizePx = new LVector2Int();
	public static LVector2Int size;

	private static final Random rand = new Random();

	private LVector2Int[] pathNodes;
	
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
	
	public Map(){
		size = new LVector2Int();
		loadTiles();
		generateRenderQueue();
		//insertPathNodes();
	}

	private void generateRenderQueue() {
		renderQueue = new TileType[size.x][size.y];
		
		//Renderqueue must be inverted, since in OpenGL y=0 
		//is at the bottom of the screen
		
		for (int y = 0; y < size.y; y++) {
			for (int x = 0; x < size.x; x++) {
				renderQueue[x][y] = tiles[x][y].tileType; 
			}
		}
	}

	private void loadTiles() {
		loadTilesFromFile(defaultMap);
	}

	private void generateTestTiles(){
		size.x = 8;
		size.y = 12;
		tiles = new Tile[size.x][size.y];
		for (int i = 0; i < size.x; i++) {
			for (int j = 0; j < size.y; j++) {
				tiles[i][j] = new Tile(gamePointers.tileSet.tileTypes[0]);
			}
		}
	}
	
	private void loadTilesFromFile(int res) {

		LSurfaceViewPanel panel = gamePointers.panel;
		InputStream inputStream = panel.getResources().openRawResource(res);

		AssetManager.AssetInputStream reader = (AssetManager.AssetInputStream) inputStream;

		TileType[] tileTypes = gamePointers.tileSet.tileTypes;

		//if(reader.nextInt() != 11) return;
		//byte[] workspaceBytes = new byte[4]; //Used to read ints

		try {
			//reader.read(workspaceBytes,0,4);

			//mapSize.x = byteArrayToInt(workspaceBytes);
			int id = reader.read(); //The version of the file
			size.x = reader.read();
			sizePx.x = size.x * TILE_SIZE;
			size.y = reader.read();
			sizePx.y = size.y * TILE_SIZE;
			
			tiles = new Tile[size.x][size.y];

			int tileID;
			int index;
			for (int y = 0; y < size.y; y++) {
				for (int x = 0; x < size.x; x++) {
					tileID = reader.read(); 
					index = gamePointers.tileSet.tileIDtoIndexMap.get(tileID);
					
					//Fix the fucked up way the maps are saved. origo is saved as 
					//top-right, but here it`s bottom left...
					tiles[x][size.y-y-1] = new Tile(tileTypes[index]);
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
		int count = size.x * size.y * NODE_DENSITY;
		pathNodes = new LVector2Int[count];
		boolean blocked = true;
		for(int i = 0; i < count; i++){
			while(blocked){
				pathNodes[i] = new LVector2Int(rand.nextInt(size.x),
						rand.nextInt(size.y));


				Tile cTile = 
					tiles[pathNodes[i].x/TILE_SIZE][pathNodes[i].y/TILE_SIZE];

				int localX = (pathNodes[i].x%TILE_SIZE)/BLOCK_SIZE;
				int localY = (pathNodes[i].y%TILE_SIZE)/BLOCK_SIZE;

				blocked = cTile.isBlocked(LMovementType.Walk, 
						localX, 
						localY);
			}
		}
	}

	public boolean isPositionBlocked(int x, int y, LMovementType mType){
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
	
	
	public void spawnNPCs(int nr, int difficulty){
		InfectoGameObjectHandler handler = GameActivity.infectoPointers.gameObjectHandler;
		for (int i = 0; i < nr; i++){
			InfectoGameObject newHuman = SpawnPool.spawnHuman(-1,-1,2*difficulty);
			handler.add(newHuman);
		}
	}
	
	@Override
	public void update(float dt, LBaseObject parent) {
		// TODO Auto-generated method stub
		
	}
}
