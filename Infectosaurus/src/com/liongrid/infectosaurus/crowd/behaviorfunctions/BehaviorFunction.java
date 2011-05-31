package com.liongrid.infectosaurus.crowd.behaviorfunctions;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.infectosaurus.crowd.State;
import com.liongrid.infectosaurus.crowd.StateList;

/**
 * @author lastis
 * A behavior function is class that changes the likelihood that a 
 * specific states will be initiated. 
 */
public abstract class BehaviorFunction extends BaseObject{

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
			prob[i] *= sigmoid(x); 
		}
	}

	protected float sigmoid(float r,float alpha) {
		return (float) (1/(1 + Math.exp(-r*alpha)));
	}
	
	protected float sigmoid(float r){
		return sigmoid(r, 1);
	}
	/**
	 * @param s the state to have its probability calculated
	 * @return any real number
	 */
	protected abstract float evaluate(State s, StateList prevStates);
}
