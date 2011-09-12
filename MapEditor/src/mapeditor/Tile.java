package mapeditor;

import java.util.ArrayList;

public class Tile {
	byte currentImgID = -1;
	private ArrayList<CollisionObject> collideables = new ArrayList<CollisionObject>();
	
	public Tile() {
	}
	
	//Situations etc goes here
	
	public void addCollideable(CollisionObject collideable){
		collideables.add(collideable);
	}
	
	public void removeCollideable(CollisionObject collideable){
		collideables.remove(collideable);
	}
	
	public Tile(byte imgID) {
		this.currentImgID = imgID;
	}


	public LImage getLImage(){
		return CData.images.get(currentImgID);
	}

	public byte getLImageID() {
		return currentImgID;
	}
	
	public boolean hasLImage(){
		return CData.images.get(currentImgID) != null;
	}

	public void setLImageID(byte curTileID) {
		this.currentImgID = curTileID;
	}
	
	public void removeLImage(){
		currentImgID = -1;
	}
	
	public void mergeWith(Tile tile){
		collideables.addAll(tile.collideables);
	}
	
	public void seperateFrom(Tile tile){
		collideables.removeAll(tile.collideables);
	}
	
}
