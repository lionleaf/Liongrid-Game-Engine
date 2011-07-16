package com.liongrid.infectosaurus.crowd.behaviorfunctions;

import android.util.Log;

import com.liongrid.gameengine.tools.MovementType;
import com.liongrid.infectosaurus.crowd.State;
import com.liongrid.infectosaurus.crowd.StateList;

public class AvoidObstacles extends BehaviorFunction{


	@Override
	protected double evaluate(State s, StateList lastStates) {
		
		if(gamePointers.map.isPositionBlocked(
				(int)s.pos.x, (int)s.pos.y, MovementType.Walk)){
			return Double.NaN;
		}
		
		return 1;
	}

}
