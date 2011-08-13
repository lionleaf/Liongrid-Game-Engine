package com.liongrid.infectosaurus.crowd.actions;

import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.crowd.State;

public class Stand extends Action {
	
	public Stand(){
		stateList.add(new State());
		mName = "Stand";
	}

	public LFixedSizeArray<State> getInternalNextStates(State lastState, float dt, InfectoGameObject parent) {
		State s = stateList.get(0);
		s.copy(lastState);
		s.action = this;
		
		return stateList;
	}
	@Override
	public int getNumberOfStates() {
		return 1;
	}

}
