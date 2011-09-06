package com.liongrid.infectomancer.components;

import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.infectomancer.IGameObject;
import com.liongrid.infectomancer.IGameObjectHandler;
import com.liongrid.infectomancer.IGamePointers;
import com.liongrid.infectomancer.ITeam;

public class IAggressivMoveComponent extends LComponent<IGameObject>{
	
	private IGameObjectHandler gameObjHandler;

	public IAggressivMoveComponent(){
		gameObjHandler = IGamePointers.gameObjectHandler;
	}

	@Override
	public void update(float dt, IGameObject parent) {
		//gameObjHandler.getClosest(parent.mPos, ITeam.Human, parent, true);
		LGameObject target = gameObjHandler.getClosestOld(parent.mPos, ITeam.Human, parent);
		if(target == null) return;
		parent.mVel.set(target.mPos);
		parent.mVel.subtract(parent.mPos);
		parent.mVel.normalize();
		parent.mVel.multiply(parent.speed);
	}
}
