package com.liongrid.infectosaurus.crowd.behaviorfunctions;

import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.tools.LMovementType;
import com.liongrid.infectosaurus.crowd.IState;
import com.liongrid.infectosaurus.crowd.IStateList;

public class IAvoidObstacles extends IBehaviorFunction{


	@Override
	protected double evaluate(IState s, IStateList lastStates) {
		
		if(LGamePointers.map.isPositionBlocked(
				(int)s.pos.x, (int)s.pos.y, LMovementType.Walk)){
			return Double.NaN;
		}
		
		return 1;
	}

}
