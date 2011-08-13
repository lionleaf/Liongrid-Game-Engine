package com.liongrid.infectosaurus.crowd.behaviorfunctions;

import android.util.Log;

import com.liongrid.gameengine.tools.LMovementType;
import com.liongrid.infectosaurus.crowd.State;
import com.liongrid.infectosaurus.crowd.StateList;

public class AvoidObstacles extends BehaviorFunction{


	@Override
	protected double evaluate(State s, StateList lastStates) {
		
		if(gamePointers.map.isPositionBlocked(
				(int)s.pos.x, (int)s.pos.y, LMovementType.Walk)){
			return Double.NaN;
		}
		
		return 1;
	}

}
