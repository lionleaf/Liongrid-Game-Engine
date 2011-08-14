package com.liongrid.infectosaurus.crowd.situations;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.infectosaurus.IGameObject;
import com.liongrid.infectosaurus.components.IBehaviorComponent;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.IBehaviorFunction;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.IPauseALotBehavior;

public class ISimpleBehaviorSituation extends ISituation {
	
	
	private IBehaviorFunction mComp = new IPauseALotBehavior();
	
	public void set(IBehaviorFunction comp){
		mComp = comp;
	}
	
	@Override
	void apply(IGameObject go, IBehaviorComponent comp) {
		comp.addBehaviorFunction(mComp);
	}

	@Override
	void remove(IGameObject go, IBehaviorComponent comp) {
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
