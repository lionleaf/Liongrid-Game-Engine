package com.liongrid.infectosaurus.crowd.actions;

import android.location.Address;

import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.crowd.State;

public class Walk extends Action {
	
	
	
	public Walk(){
		for (int i = 0; i < 5; i++) {
			stateList.add(new State());
		}
		mName = "Walk";
	}

	@Override
	public LFixedSizeArray<State> getInternalNextStates(State lastState, float dt, InfectoGameObject parent) {
		int l = 5;
		for (int i = 0; i < l; i++) {
			State state = stateList.get(i);
			
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
	
}
