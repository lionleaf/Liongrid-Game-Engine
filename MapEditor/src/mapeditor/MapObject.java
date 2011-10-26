package mapeditor;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Iterator;

import com.liongrid.gameengine.tools.LVector2;
import com.liongrid.gameengine.tools.LVector2Int;

public class MapObject{
	/**
	 * How big matrix the map object shows in the MapO panel.
	 */
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
	
	public ArrayList<CollisionObject> getCollideables(){
		return collideables;
	}
	
	
	public  Object[] getCollisionArray(){
		return collideables.toArray();
	}
	
	public Iterator<CollisionObject> collideablesIterator(){
		return collideables.iterator();
	}
	
	public StaticObject createStaticObject(float x, float y){
		return new StaticObject(this, x, y);
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
		private LVector2 arrayPos;
		private MapObject parent;
		
		public StaticObject(MapObject owner, float x, float y) {
			arrayPos = new LVector2(x, y);
			pos = new LVector2();
			updatePos();
			parent = owner;
		}
		
		public void changeParent(MapObject newParent){
			parent = newParent;
		}
		
		public LVector2 getArrayPos(){
			updateArrayPos();
			return arrayPos;
		}

		@Override
		public LVector2 getPos() {
			updatePos();
			return pos;
		}
		
		public void setPos(float x, float y){
			pos.x = x;
			pos.y = y;
			updateArrayPos();
		}
		
		public void setArrayPos(float x, float y){
			arrayPos.x = x;
			arrayPos.y = y;
			updatePos();
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
		
		private void updateArrayPos(){
			arrayPos.set(MapData.fromIsoToCartX((int) pos.x, (int) pos.y), 
					     MapData.fromIsoToCartY((int) pos.x, (int) pos.y));
		}
		
		private void updatePos(){
			pos.set(MapData.fromCartToIsoX(arrayPos.x, arrayPos.y), 
					MapData.fromCartToIsoY(arrayPos.x, arrayPos.y));
		}
	}
}
