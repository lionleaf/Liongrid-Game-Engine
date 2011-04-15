package com.infectosaurus.behaviorfunctions;

import com.infectosaurus.BaseObject;
import com.infectosaurus.states.State;

/**
 * @author lastis
 * A behavior function is class that changes the likelihood that a 
 * specific states will be initiated. 
 */
public abstract class BehaviorFunction extends BaseObject{
	double alpha = 1;

	public void update(State[] s, float[] prob){
		int i;
		float x;
		for(i = 0; i < prob.length; i++){
			x = evaluate(s[i]);
			prob[i] = (float) Math.signum(x*alpha); 
		}
	}

	/**
	 * @param s the state to have its probability calculated
	 * @return any real number
	 */
	protected abstract float evaluate(State s);
}
