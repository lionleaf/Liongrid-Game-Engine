package com.liongrid.infectomancer.crowd.situations;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.infectomancer.IGameObject;
import com.liongrid.infectomancer.components.IBehaviorComponent;

public class ISpeedSituation extends ISituation {

	private int mSpeed = 50;
	
	public void set(int speed){
		mSpeed = speed;
	}
	
	@Override
	void apply(IGameObject go, IBehaviorComponent comp) {
		go.speed += mSpeed;
	}

	@Override
	void remove(IGameObject go, IBehaviorComponent comp) {
		go.speed -= mSpeed;

	}

	@Override
	public void update(float dt, LBaseObject parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
