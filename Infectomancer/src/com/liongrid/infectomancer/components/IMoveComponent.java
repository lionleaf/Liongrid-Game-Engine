package com.liongrid.infectomancer.components;

import android.util.Log;

import com.liongrid.gameengine.LAnimationCodes;
import com.liongrid.gameengine.LComponent;
import com.liongrid.infectomancer.IGameObject;

public class IMoveComponent extends LComponent<IGameObject> {
	@Override
	public void update(float dt, IGameObject parent) {
		parent.mPos.add(parent.mVel.x * dt, parent.mVel.y * dt);
	}
	
}
