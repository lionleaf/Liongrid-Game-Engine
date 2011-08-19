package com.liongrid.infectosaurus.components;

import com.liongrid.gameengine.LComponent;
import com.liongrid.infectosaurus.IGameObject;
import com.liongrid.infectosaurus.IGameObjectHandler;
import com.liongrid.infectosaurus.IGamePointers;
import com.liongrid.infectosaurus.ITeam;

public class IAggressivMoveComponent extends LComponent<IGameObject>{
	
	private IGameObjectHandler gameObjHandler;

	public IAggressivMoveComponent(){
		gameObjHandler = IGamePointers.gameObjectHandler;
	}

	@Override
	public void update(float dt, IGameObject parent) {
		IGameObject target = gameObjHandler.getClosest(parent.mPos, ITeam.Human, parent);
		if(target == null) return;
		parent.mVel.set(target.mPos);
		parent.mVel.subtract(parent.mPos);
		parent.mVel.normalize();
		parent.mVel.multiply(parent.speed);
	}
}
