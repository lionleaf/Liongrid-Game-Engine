package com.liongrid.infectosaurus.crowd.behaviorfunctions;

import com.liongrid.infectosaurus.crowd.State;
import com.liongrid.infectosaurus.crowd.StateList;

public class PauseALotBehavior extends BehaviorFunction {

	@Override
	protected double evaluate(State s, StateList prevStates) {
		if(s.action.getName() == "Stand"){
			return HIGH;
		}
		return INDIFFERENT;
	}

}
