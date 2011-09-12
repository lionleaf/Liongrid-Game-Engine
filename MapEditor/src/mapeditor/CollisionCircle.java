package mapeditor;

import com.liongrid.gameengine.tools.LMovementType;

public class CollisionCircle extends CollisionObject implements LShape.Circle{

	private float radius;

	public CollisionCircle(float x, float y, LMovementType type, float radius) {
		super(x, y, type);
		this.radius = radius;
	}

	@Override
	public int getShape() {
		return LShape.CIRCLE;
	}

	@Override
	public float getRadius() {
		return radius;
	}

}
