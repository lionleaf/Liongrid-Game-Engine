package mapeditor;

import com.liongrid.gameengine.tools.LVector2;

public class MapObject implements LShape.Square{
	private static final float MIN_WIDTH = 20;
	private static final float MIN_HEIGHT = 20;
	private Tile[][] tiles;
	private LImage lImage;
	private LVector2 pos;
	private LVector2 imagePos;
	
	public MapObject(int sqSizeX, int sqSizeY) {
		tiles = new Tile[sqSizeX][sqSizeY];
		pos = new LVector2();
	}
	
	public MapObject(int sqSizeX, int sqSizeY, LImage image) {
		tiles = new Tile[sqSizeX][sqSizeY];
		pos = new LVector2();
		setLImage(image);
	}
	
	public void setLImage(LImage lImage) {
		this.lImage = lImage;
	}

	public LVector2 getImagePos(){
		return imagePos;
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
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				tiles[x][y].removeCollideable(collideable);
			}
		}
	}
	
	public void addCollideable(CollisionObject collideable, LVector2 pos){
		if(pos.x < 0 || pos.y < 0 || 
				pos.x > tiles.length || pos.y > tiles[0].length) return;
		
		if(pos.x == tiles.length || pos.y == tiles[0].length){
			int x = pos.x == tiles.length ? tiles.length - 1 : (int) pos.x;
			int y = pos.y == tiles[0].length ? tiles[0].length - 1 : (int) pos.y;
			tiles[x][y].addCollideable(collideable);
			return;
		}
		tiles[(int) pos.x][(int) pos.y].addCollideable(collideable);
	}
	
	public void changePosition(LVector2 pos){
		removeFromMap();
		copyToMap(pos);
	}
	
	public void copyToMap(LVector2 pos){
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
//				CData.level[x][y].mergeWith(tile)
			}
		}
	}
	
	public void removeFromMap(){
		
	}

}
