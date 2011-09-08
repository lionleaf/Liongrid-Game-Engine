package com.liongrid.thumbfighter.components;

import com.liongrid.gameengine.LCollisionObject;
import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.thumbfighter.TGameObject;

public class TExplosiveCollisionComponent extends LComponent<TGameObject> {

	@Override
	public void update(float dt, TGameObject parent) {
		if(parent == null) return;
		LCollisionObject collisionObject = parent.hitBox;
		if(collisionObject.collisionCnt > 0) explode(parent);

	}

	private void explode(TGameObject parent) {
		LGamePointers.root.remove(parent);
	}

}
