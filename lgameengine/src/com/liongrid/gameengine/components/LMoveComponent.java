package com.liongrid.gameengine.components;

import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LGameObject;

public class LMoveComponent extends LComponent<LGameObject<?>> {
	@Override
	public void update(float dt, LGameObject<?> parent) {
		parent.mPos.add(parent.mVel.x * dt, parent.mVel.y * dt);
	}
	
}
