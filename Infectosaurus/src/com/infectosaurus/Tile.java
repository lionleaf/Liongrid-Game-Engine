package com.infectosaurus;

public class Tile {
	private int 	id;
	private boolean[][] blocked;
	public int posX;
	public int posY;
	
	Tile(int id, int tileX, int tileY, boolean[][] blocked ){
		this.id = id;
		posX = tileX;
		posY = tileY;
		this.blocked = blocked;
	}
	
	public boolean isBlocked(int x, int y){
		return false;
	}	
}
