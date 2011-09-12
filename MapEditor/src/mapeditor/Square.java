package mapeditor;

import java.awt.Image;

public class Square {
	private static final int DEFAULT_SIZE = 10;
	byte currentTileID = 0;
	private CollisionObject[] collideables = new CollisionObject[DEFAULT_SIZE];
	
	//Situations etc goes here
	
	public Square(byte tileID) {
		this.currentTileID = tileID;
	}

	public Square() {
	}

	public LImage getTile(){
		return CData.tiles.get(currentTileID);
	}

	public byte getTileID() {
		return currentTileID;
	}

	public void setTileID(byte curTileID) {
		this.currentTileID = curTileID;
		
	}
}
