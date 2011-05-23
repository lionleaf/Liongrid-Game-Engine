package com.infectosaurus.map;

import android.content.Context;

import com.infectosaurus.BaseObject;
import com.infectosaurus.DrawableBitmap;

/**
 * Representation of one type of Tile. 
 * Contains the texture, and can be drawn. 
 * It also has information on what parts blocks
 * what sort of movement. 
 *
 */
public class TileType extends DrawableBitmap{
	/**
	 * You probably want to use 
	 * Tile.isBlocked(MovementType mType, int x, int y) 
	 * instead. Unless you want to remove a method call.
	 * 
	 * 
	 * The tile is divided into n equal squares, each with its local
	 * coordinate (x,y). To check whether a part of the tile is blocked
	 * check:
	 * 
	 * blocked[MovementType.ordinal()][local x][local y]
	 * 
	 * So if a square is divided in 4, there is 4 valid coordinates:
	 * (0,0), (1,0), (0,1), and (1,1);
	 * 
	 * Made public for speed, DO NOT ALTER!
	 */
	public boolean[][][] blocked;
	
	
	/**
	 * @param resource - The resource ID. Get it from XML or R.drawable
	 * @param blocked - @see{blocked}
	 * @param size - The size of the tile. Should probably be the same for all of them,
	 *  as calculations depend upon it
	 * @param context - gamePointer.panel;
	 */
	public TileType(int resource, boolean[][][] blocked){
		super(resource, Level.TILE_SIZE, Level.TILE_SIZE, BaseObject.gamePointers.panel.getContext());
		
		this.blocked = blocked;	
	}
}
