package com.infectosaurus.maptiles;

import com.infectosaurus.DrawableBitmap;
import com.infectosaurus.MovementType;

import android.content.Context;
import android.graphics.Bitmap;

public class TileType extends DrawableBitmap{
	private boolean[][][] blocked;
	
	
	public TileType(int resource, boolean[][][] blocked, int size, Context context){
		super(resource, size, size, context);
		this.blocked = blocked;
	}
	
	public boolean isBlocked(MovementType type, int x, int y){
		try{
			return blocked[x][y][type.ordinal()];		
		}
		catch (Exception e) {
			return false;
		}
	}
}
