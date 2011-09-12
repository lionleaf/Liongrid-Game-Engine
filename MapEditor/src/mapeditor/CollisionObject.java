package mapeditor;

import com.liongrid.gameengine.tools.LMovementType;
import com.liongrid.gameengine.tools.LVector2;

public abstract class CollisionObject implements LShape{
	LVector2 pos;
	private int collisionType = -1;
	
	public CollisionObject(float x, float y, LMovementType type) {
		pos = new LVector2(x, y);
		collisionType = type.ordinal();
	}

	@Override
	public LVector2 getPos() {
		return pos;
	}

}
