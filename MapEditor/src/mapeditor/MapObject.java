package mapeditor;

import com.liongrid.gameengine.tools.LVector2;

public class MapObject implements LShape.Square{
	private Square[] squares;
	private LImage image;
	private LVector2 pos;
	private LVector2 imagePos;
	private float width = 0;
	private float height = 0;
	
	public MapObject() {
		pos = new LVector2();
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
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}

}
