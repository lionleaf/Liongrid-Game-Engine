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

	static final float EXTREMLY_HIGH = 10;
	static final float VERY_HIGH = 5;
	static final float HIGH = 1.4f;
	static final float INDIFFERENT = 0;
	static final float LOW = -1.4f;
	static final float VERY_LOW = -5;
	static final float EXTREMLY_LOW = -10;
	
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
	 * The update method of BehaviorFuction calls evaluate on each element 
	 * in the state array it receives.
	 * @param s the state to have its probability calculated
	 * @return any real number. Probable values should be between about -5 and 5.
	 */
	protected abstract float evaluate(State s, StateList prevStates);
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(float dt, BaseObject parent) {
		// TODO Auto-generated method stub
		
	}
}
