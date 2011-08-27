package mapeditor;

import java.awt.Image;

public class Square {
	byte currentTileID = 0;
	
	//Situations etc goes here
	
	public Square(byte tileID) {
		this.currentTileID = tileID;
	}

	public Square() {
		// TODO Auto-generated constructor stub
	}

	public Tile getTile(){
		return CData.tiles.get(currentTileID);
	}

	public byte getTileID() {
		return currentTileID;
	}

	public void setTileID(byte curTileID) {
		this.currentTileID = curTileID;
		
	}
}
