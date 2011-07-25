package com.liongrid.infectosaurus.components;

import android.util.Log;

import com.liongrid.gameengine.CollisionHandler;
import com.liongrid.gameengine.CollisionObject;
import com.liongrid.gameengine.Component;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.InfectoGameObjectHandler;
import com.liongrid.infectosaurus.Main;
import com.liongrid.infectosaurus.Team;

public class AggressivMoveComponent extends Component<InfectoGameObject>{
	
	private InfectoGameObjectHandler gameObjHandler;

	public AggressivMoveComponent(){
		gameObjHandler = GameActivity.infectoPointers.gameObjectHandler;
	}

	@Override
	public void update(float dt, InfectoGameObject parent) {
		CollisionHandler collisionHandler = gameObjHandler.mCollisionHandler;
		
		CollisionObject collisionObject = 
			collisionHandler.getClosest(parent.pos, Team.Human.ordinal());
		if(collisionObject == null) return;
		InfectoGameObject target = (InfectoGameObject) collisionObject.owner;
		parent.vel.set(target.pos);
		parent.vel.subtract(parent.pos);
		parent.vel.normalize();
		parent.vel.multiply(parent.speed);
	}
}
