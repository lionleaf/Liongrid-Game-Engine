package com.liongrid.infectosaurus.map;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.tools.MovementType;

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
	 * @return true if x and y are out of bounds
	 */
	public boolean isBlocked(MovementType mType, int x, int y) {
		if(x >= tileType.blocked[mType.ordinal()].length 
				|| y >= tileType.blocked[mType.ordinal()][0].length
				|| x < 0 || y < 0){
			return true;
		}
		
		return tileType.blocked[mType.ordinal()][x][y];
	}
	
	 
	
}
