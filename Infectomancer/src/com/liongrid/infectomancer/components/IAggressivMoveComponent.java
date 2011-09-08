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
		IGameObject target = gameObjHandler.getClosestOld(parent.pos, ITeam.Human, parent);
		if(target == null) return;
		parent.vel.set(target.pos);
		parent.vel.subtract(parent.pos);
		parent.vel.normalize();
		parent.vel.multiply(parent.speed);
	}
}
