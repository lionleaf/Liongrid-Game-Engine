package com.liongrid.infectosaurus.crowd.behaviorfunctions;

import com.liongrid.infectosaurus.crowd.IState;
import com.liongrid.infectosaurus.crowd.IStateList;

public class IPauseALotBehavior extends IBehaviorFunction {

	@Override
	protected double evaluate(IState s, IStateList prevStates) {
		if(s.action.getName() == "IStand"){
			return HIGH;
		}
		return INDIFFERENT;
	}

}
