package com.liongrid.infectosaurus.crowd.behaviorfunctions;

import com.liongrid.infectosaurus.crowd.IState;
import com.liongrid.infectosaurus.crowd.IStateList;

public class IMoveTowards extends IBehaviorFunction{

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected double evaluate(IState s, IStateList lastStates) {
		return 1;
		//return s.turnAngle > 0 ? 3f : -3f;
	}

}
