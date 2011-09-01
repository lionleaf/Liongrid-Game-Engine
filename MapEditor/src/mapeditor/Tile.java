package mapeditor;

import java.awt.Image;

/**
 * @author lionleaf
 *	Representation of a single tile. 
 *	There should not be one instance per square, that is what squares are for!
 */
public class Tile {
	private boolean[][][] tileStates;
	private Image image;
//	private static final String[] states = CData.moveTypes;
//	private static final int TILE_BLOCKS = CData.TILE_BLOCKS;
//	private static final int STATES = states.length;
	
	byte id = -1;
	private String resource;
	private String fileName;
	
	public Tile(Image image, byte id, String resource){
//		tileStates = new boolean[TILE_BLOCKS][TILE_BLOCKS][STATES];
		this.image = image;
		this.id = id;
		
		this.fileName = resource;
		
		//Make it match resource mapping for android
		this.resource = resource.replaceAll(".png", "");
	}
	
	private void falseify(boolean[][][] array){
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				for (int k = 0; k < array[0][0].length; k++) {
					array[i][j][k] = false;
				}
			}
		}
	}
	
	public Image getImage() {
		return image;
	}

	public void setTileState(int x, int y, int dimension, boolean value){
		tileStates[x][y][dimension] = value;
	}
	
	/**
	 * @return the raw array
	 */
	public boolean[][][] getTileStates(){
		return tileStates;
	}

	public Integer getID() {
		return new Integer((int)id);
	}

	public String getResource() {
		return resource;
	}
	
	public String toString(){
		return fileName.substring(0, fileName.indexOf('.'));
	}

	public byte getIDbyte() {
		return id;
	}
}
