package com.liongrid.infectosaurus.crowd.behaviorfunctions;

import com.liongrid.infectosaurus.crowd.State;
import com.liongrid.infectosaurus.crowd.StateList;

public class MoveTowards extends BehaviorFunction{

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected double evaluate(State s, StateList lastStates) {
		return 1;
		//return s.turnAngle > 0 ? 3f : -3f;
	}

}
