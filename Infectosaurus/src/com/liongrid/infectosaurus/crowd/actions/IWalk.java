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
	public static final String WALK_NORTH_EAST = ISpawnPool.WALK_NORTH_EAST;
	public static final String WALK_NORTH_WEST = ISpawnPool.WALK_NORTH_WEST;
	public static final String WALK_SOUTH_EAST = ISpawnPool.WALK_SOUTH_EAST;
	public static final String WALK_SOUTH_WEST = ISpawnPool.WALK_SOUTH_WEST;
	
	public IWalk(){
		for (int i = 0; i < 5; i++) {
			stateList.add(new IState());
		}
		mName = WALK_SOUTH;
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
		final float absAB = 
			(float) Math.sqrt(state.vel.x * state.vel.x + state.vel.y * state.vel.y);
		final float cosAB = state.vel.x / absAB;
		final float sinAB = state.vel.y / absAB;
		
		
		boolean facingUp = sinAB > 0 ? true : false;
		
		String result = WALK_SOUTH;
		
		// 8 directions
			final float cos0Deg = 1;
		final float cos180Deg = -1;
		final float cos22Deg = 0.92388f;
		final float cos67Deg = 0.38268f;
		final float cos112Deg = - cos67Deg;
		final float cos157Deg = - cos22Deg;
			
		if(cosAB > cos22Deg){
			result = WALK_EAST;
		}else if(cosAB > cos67Deg){
			result = facingUp ? WALK_NORTH_EAST : WALK_SOUTH_EAST; 
		}else if(cosAB > cos112Deg){
			result = facingUp ? WALK_NORTH : WALK_SOUTH;
		}else if(cosAB > cos157Deg){
			result = facingUp ? WALK_NORTH_WEST : WALK_SOUTH_WEST;
		}else if(cosAB >= cos180Deg){
			result = WALK_NORTH_WEST;
		}
		
		return mName;
	}
	
}
