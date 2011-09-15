package mapeditor;

import java.awt.Image;

/**
 * @author lionleaf
 *	Representation of a single tile. 
 *	There should not be one instance per square, that is what squares are for!
 */
public class LImage {
	private Image image;
	short id = 0;
	private String resource;
	private String fileName;
	private float scale;
	
	public LImage(Image image, short id, String resource){
		this.image = image;
		this.id = id;
		this.fileName = resource;
		//Make it match resource mapping for android
		this.resource = resource.replaceAll(".png", "");
	}
	
	public Image getImage() {
		return image;
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

	public short getIDbyte() {
		return id;
	}
	
	public int getWidth(){
		int result = (int) (image.getWidth(null)*scale);
		if(result < 0) return -1;
		return result;
	}
	
	public int getHeigth(){
		int result = (int) (image.getHeight(null)*scale);
		if(result < 0) return -1;
		return result;
	}
	
}
