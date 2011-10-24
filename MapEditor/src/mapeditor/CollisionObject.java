package mapeditor;

import com.liongrid.gameengine.tools.LMovementType;
import com.liongrid.gameengine.tools.LVector2;

public abstract class CollisionObject implements LShape{
	LVector2 pos;
	/**
	 * This corresponds to what movement type that the collision object should block
	 */
	
	public CollisionObject(float x, float y) {
		pos = new LVector2(x, y);
	}

	@Override
	public LVector2 getPos() {
		return pos;
	}
	
	public void changePos(float x, float y){
		pos.x = x;
		pos.y = y;
	}

}
