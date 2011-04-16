package com.infectosaurus.map;

import com.infectosaurus.BaseObject;
import com.infectosaurus.DrawableBitmap;
import com.infectosaurus.MovementType;

import android.content.Context;
import android.graphics.Bitmap;



/**
 * Representation of a single tile. It contains info on which tileType
 * as well as specific situations currently active in it. Since tileType is static
 * this cannot be pooled!
 *
 */
public class Tile {
	
	
	/**
	 * A pointer to the tileType of this tile.
	 * 
	 * Made public for speed. Read only! DO NOT ALTER!
	 */
	public final TileType tileType;
	
	public Tile(TileType tileType){
		this.tileType = tileType;
	}
	
	/**
	 * Checks if a sub
	 * 
	 * @param mType
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isBlocked(MovementType mType, int x, int y) {
		return tileType.blocked[mType.ordinal()][x][y];
	}
	
	 
	
}
