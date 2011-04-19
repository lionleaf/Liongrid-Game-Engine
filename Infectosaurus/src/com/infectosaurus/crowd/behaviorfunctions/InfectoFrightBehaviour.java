package com.infectosaurus.crowd.behaviorfunctions;

import com.infectosaurus.Infectosaurus;
import com.infectosaurus.crowd.State;

public class InfectoFrightBehaviour extends BehaviorFunction {
	Infectosaurus inf = null;
	static final float nr = (float) ((Math.PI*Math.PI)/8);
	@Override
	protected float evaluate(State s, State lastState) {
		inf = gamePointers.currentSaurus;
		if(inf == null) return 1;
		
		float cDist = s.pos.distance2(inf.pos);
		float lastDist = lastState.pos.distance2(inf.pos);
		float dtPos = lastState.pos.distance2(s.pos);
		float dtDist = (cDist - lastDist); //Negative if we`re getting closer
		return (10*dtDist/dtPos); //Returns 10 if all distance was moved away
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
