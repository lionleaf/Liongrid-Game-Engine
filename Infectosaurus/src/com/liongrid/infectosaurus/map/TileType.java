package com.liongrid.infectosaurus.map;

import android.content.Context;
import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.Texture;

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
	 * coordinate (x,y). To check whether a part of the tile is mBlocked
	 * check:
	 * 
	 * mBlocked[MovementType.ordinal()][local x][local y]
	 * 
	 * So if a square is divided in 4, there is 4 valid coordinates:
	 * (0,0), (1,0), (0,1), and (1,1);
	 * 
	 * Made public for speed, DO NOT ALTER!
	 */
	public boolean[][][] mBlocked;
	
	
	/**
	 * @param resource - The resource ID. Get it from XML or R.drawable
	 * @param mBlocked - @see{mBlocked}
	 * @param size - The size of the tile. Should probably be the same for all of them,
	 *  as calculations depend upon it
	 * @param context - gamePointer.panel;
	 */
	public TileType(Texture texture, boolean[][][] blocked){
		super(texture, Level.TILE_SIZE, Level.TILE_SIZE);
		
		this.mBlocked = blocked;	
		
		
		/* For debugging
		 * for(boolean[][] barr : mBlocked){
			for(boolean[] ba : barr){
				for(boolean b : ba){
					if(b){
						Log.d("Hell", "yeah");
					}
				}
			}
		}*/
		
	}
}
