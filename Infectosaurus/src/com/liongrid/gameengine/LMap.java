package com.liongrid.gameengine;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import android.content.res.AssetManager;

import com.liongrid.gameengine.tools.LMovementType;
import com.liongrid.gameengine.tools.LVector2Int;
import com.liongrid.infectosaurus.IGameObject;
import com.liongrid.infectosaurus.IGameObjectHandler;
import com.liongrid.infectosaurus.IGamePointers;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.ISpawnPool;

public class LMap extends LBaseObject{

	public LTile[][] tiles;
	public LTileType[][] renderQueue;
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
	
	public LMap(){
		size = new LVector2Int();
		loadTiles();
		generateRenderQueue();
		//insertPathNodes();
	}

	private void generateRenderQueue() {
		renderQueue = new LTileType[size.x][size.y];
		
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
		tiles = new LTile[size.x][size.y];
		for (int i = 0; i < size.x; i++) {
			for (int j = 0; j < size.y; j++) {
				tiles[i][j] = new LTile(LGamePointers.tileSet.tileTypes[0]);
			}
		}
	}
	
	private void loadTilesFromFile(int res) {

		LSurfaceViewPanel panel = LGamePointers.panel;
		InputStream inputStream = panel.getResources().openRawResource(res);

		AssetManager.AssetInputStream reader = (AssetManager.AssetInputStream) inputStream;

		LTileType[] tileTypes = LGamePointers.tileSet.tileTypes;

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
			
			tiles = new LTile[size.x][size.y];

			int tileID;
			int index;
			for (int y = 0; y < size.y; y++) {
				for (int x = 0; x < size.x; x++) {
					tileID = reader.read(); 
					index = LGamePointers.tileSet.tileIDtoIndexMap.get(tileID);
					
					//Fix the fucked up way the maps are saved. origo is saved as 
					//top-right, but here it`s bottom left...
					tiles[x][size.y-y-1] = new LTile(tileTypes[index]);
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


				LTile cTile = 
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
		LTile tile = tiles[xIndex][yIndex];
		
		int localX = (x%TILE_SIZE)/BLOCK_SIZE;
		int localY = (y%TILE_SIZE)/BLOCK_SIZE;
		
		return tile.isBlocked(mType, localX, localY);
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	
	public void spawnNPCs(int nr, int difficulty){
		IGameObjectHandler handler = IGamePointers.gameObjectHandler;
		for (int i = 0; i < nr; i++){
			IGameObject newHuman = ISpawnPool.spawnHuman(-1,-1,2*difficulty);
			handler.add(newHuman);
		}
	}
	
	@Override
	public void update(float dt, LBaseObject parent) {
		// TODO Auto-generated method stub
		
	}
}
