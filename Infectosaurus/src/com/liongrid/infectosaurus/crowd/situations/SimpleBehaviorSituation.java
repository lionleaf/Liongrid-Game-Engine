package com.liongrid.infectosaurus.crowd.situations;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.components.BehaviorComponent;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.BehaviorFunction;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.PauseALotBehavior;

public class SimpleBehaviorSituation extends Situation {
	
	
	private BehaviorFunction mComp = new PauseALotBehavior();
	
	public void set(BehaviorFunction comp){
		mComp = comp;
	}
	
	@Override
	void apply(InfectoGameObject go, BehaviorComponent comp) {
		comp.addBehaviorFunction(mComp);
	}

	@Override
	void remove(InfectoGameObject go, BehaviorComponent comp) {
		comp.removeBehaviorFunction(mComp);
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
