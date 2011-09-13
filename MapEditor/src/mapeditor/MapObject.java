package mapeditor;

import java.util.ArrayList;

import com.liongrid.gameengine.tools.LVector2;

public class MapObject implements LShape.Square{
	private static final float MIN_WIDTH = 20;
	private static final float MIN_HEIGHT = 20;
	private byte LImageID = -1;
	private ArrayList<CollisionObject> collideables = new ArrayList<CollisionObject>();
	private LVector2 pos;
	
	public MapObject(){
		
	}
	
	public MapObject(LImage image) {
		setLImage(image);
	}
	
	public void setLImage(LImage lImage) {
		this.lImage = lImage;
	}

	@Override
	public int getShape() {
		return LShape.SQUARE;
	}

	@Override
	public float getWidth() {
		int result = lImage.getWidth();
		if(result < MIN_WIDTH) return MIN_WIDTH;
		return result;
	}

	@Override
	public float getHeight() {
		int result = lImage.getHeigth();
		if(result < MIN_HEIGHT) return MIN_HEIGHT;
		return result;
	}
	
	public void removeCollideable(CollisionObject collideable){
		collideables.remove(collideable);
	}
	
	public void addCollideable(CollisionObject collideable){
		collideables.add(collideable);
	}
	
	public void changePosition(LVector2 pos){
		removeFromMap();
		copyToMap(pos);
	}
	
	public void copyToMap(LVector2 pos){
		
	}
	
	public void removeFromMap(){
		
	}

	@Override
	public LVector2 getPos() {
		return null;
	}

}
