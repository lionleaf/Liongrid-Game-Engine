package com.liongrid.infectosaurus.components;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.GameObject;

public class MoveComponent extends Component {
	
	public void update(float dt, BaseObject parent) {
		GameObject gameObject = (GameObject) parent;
		gameObject.pos.add(gameObject.vel.x * dt, gameObject.vel.y * dt);
	}

}
