package com.liongrid.infectosaurus.components;

import com.liongrid.gameengine.Component;
import com.liongrid.infectosaurus.InfectoGameObject;

public class MoveComponent extends Component<InfectoGameObject> {
	@Override
	public void update(float dt, InfectoGameObject parent) {
		parent.pos.add(parent.vel.x * dt, parent.vel.y * dt);
	}
	
}
