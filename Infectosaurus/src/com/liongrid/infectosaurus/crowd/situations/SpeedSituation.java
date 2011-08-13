package com.liongrid.infectosaurus.crowd.situations;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.GameObject;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.components.BehaviorComponent;

public class SpeedSituation extends Situation {

	private int mSpeed = 50;
	
	public void set(int speed){
		mSpeed = speed;
	}
	
	@Override
	void apply(InfectoGameObject go, BehaviorComponent comp) {
		go.speed += mSpeed;
	}

	@Override
	void remove(InfectoGameObject go, BehaviorComponent comp) {
		go.speed -= mSpeed;

	}

	@Override
	public void update(float dt, BaseObject parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
