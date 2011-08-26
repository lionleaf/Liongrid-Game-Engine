package com.liongrid.infectomancer.crowd.actions;

import com.liongrid.gameengine.LAnimationCodes;
import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.infectomancer.IGameObject;
import com.liongrid.infectomancer.ISpawnPool;
import com.liongrid.infectomancer.crowd.IState;

public class IWalk extends IAction {
	
	public IWalk(){
		for (int i = 0; i < 5; i++) {
			stateList.add(new IState());
		}
		mName = LAnimationCodes.WALK_SOUTH;
	}

	@Override
	public LFixedSizeArray<IState> getInternalNextStates(IState lastState, float dt, IGameObject parent) {
		int l = getNumberOfStates();
		for (int i = 0; i < l; i++) {
			IState state = stateList.get(i);
			
			state.copy(lastState);
			
			float tempangle = dt*(float) (2*Math.PI - (i*Math.PI));
			float angle = lastState.vel.getAngle() + tempangle;
			state.vel.x = (float) (Math.cos(angle) * parent.speed);
			state.vel.y = (float) (Math.sin(angle) * parent.speed);
			state.pos.add(state.vel.x * dt, state.vel.y * dt);
			state.action = this;
		}
		return stateList;
	}


	@Override
	public int getNumberOfStates() {
		return 5;
	}

	@Override
	public String getAnimationCode(IState state) {
		return mName;
	}
	
}
