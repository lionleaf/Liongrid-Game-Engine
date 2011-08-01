package com.liongrid.infectosaurus.components;

import android.util.Log;

import com.liongrid.gameengine.Component;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.Main;

public class MoveComponent extends Component<InfectoGameObject> {
	@Override
	public void update(float dt, InfectoGameObject parent) {
		parent.pos.add(parent.mVel.x * dt, parent.mVel.y * dt);
	}
	
}
