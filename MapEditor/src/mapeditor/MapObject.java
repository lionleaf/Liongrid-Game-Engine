package mapeditor;

import java.security.acl.Owner;
import java.util.ArrayList;
import com.liongrid.gameengine.tools.LVector2;
import com.liongrid.gameengine.tools.LVector2Int;

public class MapObject{
	public int arraySizeX = 0;
	public int arraySizeY = 0;
	private static final int MIN_WIDTH = 20;
	private static final int MIN_HEIGHT = 20;
	private short lImageID = 0;
	private short id = 0;
	private float scale = 1;
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
		centerPos.x = (int) (x*scale);
		centerPos.y = (int) (y*scale);
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
	
	public int getWidth() {
		LImage img = getLImage();
		if(img == null) return MIN_WIDTH;
		int result = (int) (img.getWidth() * scale);
		if(result < MIN_WIDTH) return MIN_WIDTH;
		return result;
	}
	
	public int getHeight() {
		LImage img = getLImage();
		if(img == null) return MIN_HEIGHT;
		int result = (int) (img.getWidth() * scale);
		if(result < MIN_HEIGHT) return MIN_HEIGHT;
		return result;
	}
	
	
	public class StaticObject implements LShape.Square{

		private LVector2 pos;
		private LVector2 isoPos;
		private MapObject parent;
		
		public StaticObject(MapObject owner) {
			pos = new LVector2();
			isoPos = new LVector2();
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
			return parent.getWidth();
		}

		@Override
		public float getHeight() {
			return parent.getHeight();
		}
		
		public int getLImageID(){
			return parent.getLImageID();
		}
		
		public LImage getLImage(){
			return parent.getLImage();
		}
	}
}
