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
		int tile1 = R.drawable.scrub;
		
		int[] bitmaps = {tile1};
		tileTypes = new TileType[bitmaps.length];
		for(int i = 0; i < tileTypes.length; i++){
			tileTypes[i] = 
				new TileType(tile1, new boolean[2][2][MovementType.values().length], 
						Level.TILE_SIZE, panel.getContext());
		}
	}
}
