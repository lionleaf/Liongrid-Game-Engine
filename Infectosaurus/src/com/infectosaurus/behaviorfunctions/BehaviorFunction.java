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
			prob[i] = sigmoid(x); 
		}
	}

	/**
	 * A function to normalize any given x value to a number between 0 and 1
	 * @param x
	 * @return float between 0 and 1
	 */
	private float sigmoid(float x) {
			return (float) (1/(1+Math.exp(-alpha*x)));
	}

	/**
	 * @param s the state to have its probability calculated
	 * @return any real number
	 */
	protected abstract float evaluate(State s);
}
