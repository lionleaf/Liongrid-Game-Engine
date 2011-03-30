package com.infectosaurus;

import android.graphics.Bitmap;

public class TileType extends DrawableBitmap{
	private boolean[][][] blocked;
	
	
	TileType(Bitmap bitmap, boolean[][][] blocked, int size){
		super(bitmap, size, size);
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
