package com.infectosaurus.crowd.behaviorfunctions;

import com.infectosaurus.BaseObject;
import com.infectosaurus.FixedSizeArray;
import com.infectosaurus.crowd.State;
import com.infectosaurus.crowd.StateList;

/**
 * @author lastis
 * A behavior function is class that changes the likelihood that a 
 * specific states will be initiated. 
 */
public abstract class BehaviorFunction extends BaseObject{
	float alpha = 1;

	/**
	 * @param s - An array of States. Will be casted! BE CAREFUL!
	 * @param prob
	 */
	public void update(FixedSizeArray<State> s, 
					   float[] prob, 
					   StateList prevStates){
		int i;
		float x;
		Object[] o = s.getArray();
		int length = s.getCount();
		for(i = 0; i < length; i++){
			x = evaluate((State)o[i], prevStates);
			prob[i] *= (float) sigmoid(x); 
		}
	}

	private double sigmoid(float r) {
		return 1/(1 + Math.exp(-r*alpha));
	}

	/**
	 * @param s the state to have its probability calculated
	 * @return any real number
	 */
	protected abstract float evaluate(State s, StateList prevStates);
}
