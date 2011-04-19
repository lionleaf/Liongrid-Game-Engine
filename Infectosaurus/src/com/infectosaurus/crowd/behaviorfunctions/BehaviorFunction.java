package com.infectosaurus.crowd.behaviorfunctions;

import com.infectosaurus.BaseObject;
import com.infectosaurus.FixedSizeArray;
import com.infectosaurus.crowd.State;

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
	public void update(FixedSizeArray<State> s, float[] prob){
		int i;
		float x;
		Object[] o = s.getArray();
		int length = s.getCount();
		for(i = 0; i < length; i++){
			x = evaluate((State)o[i]);
			prob[i] *= (float) sigmoid(x*alpha); 
		}
	}

	private double sigmoid(float f) {
		return 1/(1 + Math.exp(-f*alpha));
	}

	/**
	 * @param s the state to have its probability calculated
	 * @return any real number
	 */
	protected abstract float evaluate(State s);
}
