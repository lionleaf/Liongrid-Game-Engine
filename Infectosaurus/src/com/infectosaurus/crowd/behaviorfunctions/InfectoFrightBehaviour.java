package com.infectosaurus.crowd.behaviorfunctions;

import com.infectosaurus.Infectosaurus;
import com.infectosaurus.Vector2;
import com.infectosaurus.crowd.State;
import com.infectosaurus.crowd.StateList;

public class InfectoFrightBehaviour extends BehaviorFunction {
	Infectosaurus inf = null;
	static final float nr = (float) ((Math.PI*Math.PI)/8);
	Vector2 vec = new Vector2();
	
	@Override
	protected float evaluate(State s, StateList lastStates) {
		inf = gamePointers.currentSaurus;
		if(inf == null) return 0;
		
		vec.set(inf.pos);
		vec.subtract(lastStates.get(1).pos);
		float angleToInfect = vec.getAngle();
		float lastAngle = lastStates.get(1).vel.getAngle() - angleToInfect ;
		float newAngle = s.vel.getAngle() - angleToInfect ;
		
		if((newAngle*newAngle) >= (lastAngle*lastAngle)){
			return 10;
		}else{
			return -10; 
		}
		
		
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
