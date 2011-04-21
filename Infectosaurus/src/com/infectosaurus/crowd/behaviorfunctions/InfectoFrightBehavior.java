package com.infectosaurus.crowd.behaviorfunctions;

import com.infectosaurus.Infectosaurus;
import com.infectosaurus.Vector2;
import com.infectosaurus.crowd.State;
import com.infectosaurus.crowd.StateList;

public class InfectoFrightBehavior extends BehaviorFunction {
	Infectosaurus inf = null;
	static final float nr = (float) ((Math.PI*Math.PI)/8);
	Vector2 vec = new Vector2();
	float alpha = 10;
	
	@Override
	protected float evaluate(State s, StateList lastStates) {
		inf = gamePointers.currentSaurus;
		if(inf == null) return 0;
		
		vec.set(inf.pos);
		vec.subtract(lastStates.get(1).pos);
		float angToInf = vec.getAngle();
		float away = 
			(float)(angToInf > 0 ? angToInf - Math.PI : angToInf + Math.PI);
		return gauss(s.vel.getAngle(), away, 1f);
		
	}

	private float gauss(float x, float mu, float sig2) {
		return (float) Math.exp(-Math.pow((Math.abs(x)-mu), 2)/2);
	}

	/* (non-Javadoc)
	 * @see com.infectosaurus.crowd.behaviorfunctions.BehaviorFunction#sigmoid(float)
	 */
	@Override
	protected float sigmoid(float r) {
		// TODO Auto-generated method stub
		return sigmoid(r, alpha);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
