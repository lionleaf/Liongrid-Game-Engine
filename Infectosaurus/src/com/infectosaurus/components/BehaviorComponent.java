package com.infectosaurus.components;

import com.infectosaurus.BaseObject;
import com.infectosaurus.FixedSizeArray;
import com.infectosaurus.GameObject;
import com.infectosaurus.crowd.State;
import com.infectosaurus.crowd.behaviorfunctions.BehaviorFunction;

/**
 * @author lastis
 * The behavior component makes an game object able to initiate a state
 * and chooses a number from 0 to 1 to initiate a specific state.
 * The probability of each of the states are modified by the behavior
 * functions. 
 */
public class BehaviorComponent extends Component{
	

	public static final int DEFAULT_STATES = 5;
	private final static int MAX_BEHAVIOURS = 32;
	FixedSizeArray<BehaviorFunction> behaviours = 
		new FixedSizeArray<BehaviorFunction>(MAX_BEHAVIOURS);
	float[] defaultProb = new float[MAX_BEHAVIOURS]; 
	State[] defaultStates = new State[DEFAULT_STATES];
	State curState;
	
	public BehaviorComponent() {
		for (int i = 0; i < defaultStates.length; i++) {
			defaultStates[i] = new State();
			defaultStates[i].turnAngle = (float) (Math.PI/2 - (i*Math.PI/4));
		}
		
		for (int i = 0; i < defaultStates.length; i++) {
			for (int j = 0; j < defaultStates.length; j++) {
				defaultStates[i].addNextState(defaultStates[j]);
			}
		}
		
		curState = defaultStates[2];
	}
	
	@Override
	public void update(float dt, BaseObject parent) {
		checkSituationChange();		
		
		calculateDefaultProb();
		
		curState.update(dt, parent);
		Object[] bObjects = behaviours.getArray();
		int size = behaviours.getCount();
		for (int i = 0; i < size; i++) {
			BehaviorFunction bf = (BehaviorFunction) bObjects[i];
			bf.update((State[]) curState.nextStates.getArray(), defaultProb);
		}
		
	}

	private void checkSituationChange() {
	}

	private void calculateDefaultProb() {
		for(int i = 0;  i < defaultProb.length; i++){
			if(i < curState.nextStates.getCount()) 
				defaultProb[i] = 1.0f/curState.nextStates.getCount();
			else defaultProb[i] = 0f;
		}
	}

	private void calculateStates() {
		
	}

}
