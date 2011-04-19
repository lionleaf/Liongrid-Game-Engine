package com.infectosaurus.components;

import java.util.Random;

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
	

	static Random random = new Random();
	public static final int DEFAULT_STATES = 5;
	private final static int MAX_BEHAVIOURS = 32;
	FixedSizeArray<BehaviorFunction> behaviours = 
		new FixedSizeArray<BehaviorFunction>(MAX_BEHAVIOURS);
	float[] probabilities = new float[MAX_BEHAVIOURS]; 
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
		
		float pickState = random.nextFloat();
		
		calculateDefaultProb();
		// Let the state update the pos and vel to the parent
		curState.update(dt, parent);
		((GameObject) parent).pos = curState.pos;
		((GameObject) parent).vel = curState.vel;
		// Update the next available states for the default states
		for (int i = 0; i < defaultStates.length; i++) {
			defaultStates[i].updateNextStates(dt, parent);
		}
		
		Object[] bObjects = behaviours.getArray();
		int length = behaviours.getCount();
		for (int i = 0; i < length; i++) {
			BehaviorFunction bf = (BehaviorFunction) bObjects[i];
			bf.update((State[]) curState.nextStates.getArray(), probabilities);
		}
		
	}

	private void checkSituationChange() {
	}

	private void calculateDefaultProb() {
		for(int i = 0;  i < probabilities.length; i++){
			if(i < curState.nextStates.getCount()) 
				probabilities[i] = 1.0f/curState.nextStates.getCount();
			else probabilities[i] = 0f;
		}
	}

	private void calculateStates() {
		
	}

}
