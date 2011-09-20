package mapeditor;

import java.util.ArrayList;
import com.liongrid.gameengine.tools.LVector2;
import com.liongrid.gameengine.tools.LVector2Int;

public class MapObject{
	private short lImageID = 0;
	private short id = 0;
	private ArrayList<CollisionObject> collideables = new ArrayList<CollisionObject>();
	private LVector2Int centerPos;
	private String name;
	
	public MapObject(short id){
		this(id, null, new LVector2Int());
	}
	
	public MapObject(short id, LVector2Int centerPos){
		this(id, null, centerPos);
	}

	public MapObject(short id, String name){
		this(id, name, new LVector2Int());
	}
	
	public MapObject(short id, String name, LVector2Int centerPos){
		this.id = id;
		this.centerPos = centerPos;
		this.name = name;
	}
	
	public void setName(String name){
		this.name = new String(name);
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public String toString() {
		if(name != null){
			return name;
		}
		else return super.toString();
	}
	
	public Integer getID() {
		return new Integer(id);
	}
	
	public short getIDbyte() {
		return id;
	}

	public void removeCollideable(CollisionObject collideable){
		collideables.remove(collideable);
	}
	
	public void addCollideable(CollisionObject collideable){
		collideables.add(collideable);
	}
	
	public  Object[] getCollideables(){
		return collideables.toArray();
	}
	
	public StaticObject createStaticObject(){
		return new StaticObject(this);
	}
	
	public void setCenter(int x, int y){
		centerPos.x = x;
		centerPos.y = y;
	}
	
	public LVector2Int getCenter(){
		return centerPos;
	}
	
	public LImage getLImage(){
		return CData.images.get((int) lImageID);
	}
	
	public void setLImageID(short id){
		lImageID = id;
	}
	
	public int getLImageID(){
		return lImageID;
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
			LImage img = parent.getLImage();
			if(img == null) return MIN_WIDTH;
			int result = img.getWidth();
			if(result < MIN_WIDTH) return MIN_WIDTH;
			return result;
		}

		@Override
		public float getHeight() {
			LImage img = parent.getLImage();
			if(img == null) return MIN_HEIGHT;
			int result = img.getWidth();
			if(result < MIN_HEIGHT) return MIN_HEIGHT;
			return result;
		}
	}
}
