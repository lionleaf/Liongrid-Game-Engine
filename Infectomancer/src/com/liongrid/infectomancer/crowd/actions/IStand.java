package com.liongrid.infectomancer.crowd.actions;

import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.infectomancer.IGameObject;
import com.liongrid.infectomancer.crowd.IState;

public class IStand extends IAction {
	
	public IStand(){
		stateList.add(new IState());
		mName = "IStand";
	}

	@Override
	public LFixedSizeArray<IState> getInternalNextStates(IState lastState, float dt, IGameObject parent) {
		IState s = stateList.get(0);
		s.copy(lastState);
		s.action = this;
		
		return stateList;
	}
	@Override
	public int getNumberOfStates() {
		return 1;
	}

	@Override
	public String getAnimationCode(IState state) {
		return mName;
	}

}
