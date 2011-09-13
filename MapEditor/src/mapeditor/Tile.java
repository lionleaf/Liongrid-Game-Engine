package mapeditor;

import java.util.ArrayList;

public class Tile {
	private static final int STATIC_OBJ_CAPACITY = 10;
	byte curBackgroundObjID = -1;
	byte curStaticObjID[] = new byte[STATIC_OBJ_CAPACITY];
	
	public Tile() {
	}
	
	public Tile(byte mapObjID) {
		this.curBackgroundObjID = mapObjID;
	}


	public LImage getLImage(){
		return CData.images.get(curBackgroundObjID);
	}

	public byte getLImageID() {
		return curBackgroundObjID;
	}
	
	public boolean hasLImage(){
		return CData.images.get(curBackgroundObjID) != null;
	}

	public void setLImageID(byte curTileID) {
		this.curBackgroundObjID = curTileID;
	}
	
	public void removeLImage(){
		curBackgroundObjID = -1;
	}
	
}
