package com.liongrid.infectomancer.crowd.behaviorfunctions;

import com.liongrid.infectomancer.crowd.IState;
import com.liongrid.infectomancer.crowd.IStateList;

public class IPauseALotBehavior extends IBehaviorFunction {

	@Override
	protected double evaluate(IState s, IStateList prevStates) {
		if(s.action.getName() == "IStand"){
			return HIGH;
		}
		return INDIFFERENT;
	}

}
