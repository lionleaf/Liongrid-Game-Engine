package com.infectosaurus.crowd.behaviorfunctions;

import com.infectosaurus.crowd.State;
import com.infectosaurus.crowd.StateList;

public class MoveTowards extends BehaviorFunction{

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected float evaluate(State s, StateList lastStates) {
		return 1f;
		//return s.turnAngle > 0 ? 3f : -3f;
	}

}
