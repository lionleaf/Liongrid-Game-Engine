package mapeditor;

import java.util.ArrayList;

import com.liongrid.gameengine.tools.LVector2;

public class MapObject{
	private byte lImageID = -1;
	private ArrayList<CollisionObject> collideables = new ArrayList<CollisionObject>();
	private LVector2 centerPos;
	
	public MapObject(){
		centerPos = new LVector2();
	}
	
	public MapObject(LVector2 centerPos){
		this.centerPos = centerPos;
	}

	public void removeCollideable(CollisionObject collideable){
		collideables.remove(collideable);
	}
	
	public void addCollideable(CollisionObject collideable){
		collideables.add(collideable);
	}
	
	public StaticObject createStaticObject(){
		return new StaticObject(this);
	}
	
	public void setCenter(float x, float y){
		centerPos.x = x;
		centerPos.y = y;
	}
	
	public LVector2 getCenter(){
		return centerPos;
	}
	
	public LImage getImage(){
		return CData.images.get(lImageID);
	}
	
	
	class StaticObject implements LShape.Square{
		private static final float MIN_WIDTH = 20;
		private static final float MIN_HEIGHT = 20;
		private LVector2 pos;
		private MapObject parent;
		
		public StaticObject(MapObject owner) {
			parent = owner;
		}
		
		public void changeParent(MapObject newParent){
			parent = newParent;
		}

		@Override
		public LVector2 getPos() {
			return pos;
		}

		@Override
		public int getShape() {
			return LShape.SQUARE;
		}
		
		@Override
		public float getWidth() {
			LImage img = parent.getImage();
			if(img == null) return MIN_WIDTH;
			int result = img.getWidth();
			if(result < MIN_WIDTH) return MIN_WIDTH;
			return result;
		}

		@Override
		public float getHeight() {
			LImage img = parent.getImage();
			if(img == null) return MIN_HEIGHT;
			int result = img.getWidth();
			if(result < MIN_HEIGHT) return MIN_HEIGHT;
			return result;
		}
	}
}
