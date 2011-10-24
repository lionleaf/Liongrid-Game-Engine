package mapeditor;

import com.liongrid.gameengine.tools.LMovementType;

public class CollisionSquare extends CollisionObject implements LShape.Square{

	private float width;
	private float height;

	public CollisionSquare(float x, float y, float width, float height) {
		super(x, y);
		this.width = width;
		this.height = height;
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
