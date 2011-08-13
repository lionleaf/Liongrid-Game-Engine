package com.liongrid.infectosaurus.components;

import android.util.Log;

import com.liongrid.gameengine.LCollisionHandlerMultipleArrays;
import com.liongrid.gameengine.LCollisionObject;
import com.liongrid.gameengine.LComponent;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.InfectoGameObjectHandler;
import com.liongrid.infectosaurus.Main;
import com.liongrid.infectosaurus.Team;

public class AggressivMoveComponent extends LComponent<InfectoGameObject>{
	
	private InfectoGameObjectHandler gameObjHandler;

	public AggressivMoveComponent(){
		gameObjHandler = GameActivity.infectoPointers.gameObjectHandler;
	}

	@Override
	public void update(float dt, InfectoGameObject parent) {
		InfectoGameObject target = gameObjHandler.getClosest(parent.pos, Team.Human, parent);
		if(target == null) return;
		parent.mVel.set(target.pos);
		parent.mVel.subtract(parent.pos);
		parent.mVel.normalize();
		parent.mVel.multiply(parent.speed);
	}
}
