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
		return new Integer(id);
	}

	public String getResource() {
		return resource;
	}
	
	@Override
	public String toString(){
		return resource;
	}

	public short getIDbyte() {
		return id;
	}
	
	public int getWidth(){
		return image.getWidth(null);
	}
	
	public int getHeigth(){
		return image.getHeight(null);
	}
	
}
