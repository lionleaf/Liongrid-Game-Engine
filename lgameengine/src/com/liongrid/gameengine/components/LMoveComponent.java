package com.liongrid.gameengine.components;

import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LGameObject;

public class LMoveComponent extends LComponent {
	@Override
	public void update(float dt, LGameObject parent) {
		parent.pos.add(parent.vel.x * dt, parent.vel.y * dt);
	}
	
}
