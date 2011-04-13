package com.infectosaurus.components;

import android.util.Log;

import com.infectosaurus.BaseObject;
import com.infectosaurus.GameObject;

public class MoveComponent extends Component {
	
	public void update(float dt, BaseObject parent) {
		GameObject gameObject = (GameObject) parent;
		gameObject.pos.add(gameObject.vel.x * dt, gameObject.vel.y * dt);
		
	}

}
