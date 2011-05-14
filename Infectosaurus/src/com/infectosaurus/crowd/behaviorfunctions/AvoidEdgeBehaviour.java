package com.infectosaurus.crowd.behaviorfunctions;

import com.infectosaurus.crowd.State;
import com.infectosaurus.crowd.StateList;

public class AvoidEdgeBehaviour extends BehaviorFunction {

	@Override
	protected float evaluate(State s, StateList lastStates) {
		int width = gamePointers.level.getWidth();
		int height = gamePointers.level.getHeight();
		
		float distanceToSideX =  Math.min(width-s.pos.x, s.pos.x);
		float distanceToSideY =  Math.min(width-s.pos.y, s.pos.y);
		
		
		//float nr = (height/distanceToSideY) + (width/distanceToSideX);
		
		if(distanceToSideX <= 0 || distanceToSideY <= 0) return -1000;
		
		return 1;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
