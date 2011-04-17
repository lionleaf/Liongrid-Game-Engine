package com.infectosaurus.components;

import com.infectosaurus.BaseObject;
import com.infectosaurus.FixedSizeArray;
import com.infectosaurus.behaviorfunctions.BehaviorFunction;
import com.infectosaurus.states.State;

/**
 * @author lastis
 * The behavior component makes an game object able to initiate a state
 * and chooses a number from 0 to 1 to initiate a specific state.
 * The probability of each of the states are modified by the behavior
 * functions. 
 */
public class CrowdComponent extends Component{
	
	private final static int MAX_BEHAVIOURS = 32;
	FixedSizeArray<BehaviorFunction> behaviours = 
		new FixedSizeArray<BehaviorFunction>(MAX_BEHAVIOURS);
	
	@Override
	public void update(float dt, BaseObject parent) {
		checkSituationChange();		
		
		State[] states = calculateStates();
		float[] defaultProb = calculateDefaultProb(states);
		
		Object[] bObjects = behaviours.getArray();
		int size = behaviours.getCount();
		for (int i = 0; i < size; i++) {
			BehaviorFunction bf = (BehaviorFunction) bObjects[i];
			bf.update(states, defaultProb);
		}
		
	}

	private void checkSituationChange() {
		// TODO Auto-generated method stub
		
	}

	private float[] calculateDefaultProb(State[] states) {
		// TODO Auto-generated method stub
		return null;
	}

	private State[] calculateStates() {
		return null;
	}

}
