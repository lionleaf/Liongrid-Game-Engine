package com.liongrid.infectosaurus.crowd.behaviorfunctions;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.tools.MovementType;
import com.liongrid.infectosaurus.crowd.State;
import com.liongrid.infectosaurus.crowd.StateList;

public class AvoidObstacles extends BehaviorFunction{

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected float evaluate(State s, StateList lastStates) {
		
		if(gamePointers.level.isPositionBlocked(
				(int)s.pos.x, (int)s.pos.y, MovementType.WALKING)){
			return -10000;
		}
		
		return 1;
	}

}
