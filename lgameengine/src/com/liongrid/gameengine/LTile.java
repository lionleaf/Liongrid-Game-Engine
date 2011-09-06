package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.LMovementType;
/**
 * Representation of a single tile. It contains info on which tileType
 * as well as specific situations currently active in it. Since tileType is static
 * this cannot be pooled!
 *
 */
public class LTile {
	
	
	/**
	 * A pointer to the tileType of this tile.
	 * 
	 * Made public for speed. Read only! DO NOT ALTER!
	 */
	public final LTileType tileType;
	
	public LTile(LTileType tileType){
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
	public boolean isBlocked(LMovementType mType, int x, int y) {
		if(x >= tileType.mBlocked[mType.ordinal()].length 
				|| y >= tileType.mBlocked[mType.ordinal()][0].length
				|| x < 0 || y < 0){
			return true;
		}
		
		return tileType.mBlocked[mType.ordinal()][x][y];
	}
	
}
