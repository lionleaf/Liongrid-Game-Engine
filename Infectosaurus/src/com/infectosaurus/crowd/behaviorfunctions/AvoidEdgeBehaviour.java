package com.infectosaurus.crowd.behaviorfunctions;

import com.infectosaurus.crowd.State;

public class AvoidEdgeBehaviour extends BehaviorFunction {

	@Override
	protected float evaluate(State s, State lastState) {
		int width = gamePointers.panel.getWidth();
		int height = gamePointers.panel.getHeight();
		
		float distanceToSideX =  Math.min(s.pos.x-width, s.pos.x);
		float distanceToSideY =  Math.min(s.pos.y-width, s.pos.y);
		
		
		float nr = (height/distanceToSideY) + (width/distanceToSideX);
		
		if(distanceToSideX <= 0 || distanceToSideY <= 0) return -10;
		
		return 1;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
