package com.liongrid.infectosaurus.components;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.GameObject;
import com.liongrid.infectosaurus.tools.GameObjectHandler;

public class AggressivMoveComponent extends Component{
	
	private GameObjectHandler gameObjHandler;

	public AggressivMoveComponent(){
		gameObjHandler = BaseObject.gamePointers.gameObjectHandler;
	}

	public void update(float dt, BaseObject parent) {
		GameObject gameObject = (GameObject) parent;
		GameObject target = gameObjHandler.getClosest(gameObject, gameObject.team.Human);
		if(target != null){
			gameObject.vel.set(target.pos);
			gameObject.vel.subtract(gameObject.pos);
			gameObject.vel.normalize();
			gameObject.vel.multiply(gameObject.speed);
		}
	}
}
