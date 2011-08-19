package com.liongrid.infectosaurus.crowd.actions;

import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.infectosaurus.ISpawnPool;
import com.liongrid.infectosaurus.IGameObject;
import com.liongrid.infectosaurus.crowd.IState;

public class IWalk extends IAction {
	public static final String WALK_EAST = ISpawnPool.WALK_EAST;
	public static final String WALK_WEST = ISpawnPool.WALK_WEST;
	public static final String WALK_NORTH = ISpawnPool.WALK_NORTH;
	public static final String WALK_SOUTH = ISpawnPool.WALK_SOUTH;
	
	public IWalk(){
		for (int i = 0; i < 5; i++) {
			stateList.add(new IState());
		}
		mName = WALK_NORTH;
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
	
}
