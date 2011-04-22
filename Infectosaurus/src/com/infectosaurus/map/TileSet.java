package com.infectosaurus.map;

import com.infectosaurus.BaseObject;
import com.infectosaurus.MovementType;
import com.infectosaurus.Panel;
import com.infectosaurus.R;

/**
 * The set of TileTypes. This should be loaded from a xml upon start, 
 * and should probably be initiated before level and start of rendering
 *
 */
public class TileSet {
	
	/**
	 * The tileTypes. Index should be tileID.
	 * Made public for speed. Please only read 
	 */
	public TileType[] tileTypes;
	
	public TileSet(){
		initTileTypes();
	}
	/**
	 * Load the tileSet! Must be called before any tiles can be drawn 
	 */
	public void initTileTypes(){
		Panel panel = BaseObject.gamePointers.panel;
		int tile1 = R.drawable.tile1;
		int tile2 = R.drawable.tile2;
		int tile3 = R.drawable.tile3;
		int tile4 = R.drawable.tile4;
		int tile5 = R.drawable.tile5;
		int tile6 = R.drawable.tile6;
		
		
		int[] bitmaps = {tile1,tile2,tile3,tile4,tile5,tile6};
		tileTypes = new TileType[bitmaps.length];
		for(int i = 0; i < tileTypes.length; i++){
			tileTypes[i] = 
				new TileType(bitmaps[i], new boolean[2][2][MovementType.values().length], 
						Level.TILE_SIZE, panel.getContext());
		}
	}
}
