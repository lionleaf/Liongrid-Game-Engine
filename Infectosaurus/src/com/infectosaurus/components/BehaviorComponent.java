package com.infectosaurus.components;

import java.util.Random;

import android.util.Log;

import com.infectosaurus.BaseObject;
import com.infectosaurus.FixedSizeArray;
import com.infectosaurus.GameObject;
import com.infectosaurus.crowd.State;
import com.infectosaurus.crowd.StateList;
import com.infectosaurus.crowd.behaviorfunctions.AvoidEdgeBehaviour;
import com.infectosaurus.crowd.behaviorfunctions.BehaviorFunction;
import com.infectosaurus.crowd.behaviorfunctions.InfectoFrightBehavior;
import com.infectosaurus.crowd.behaviorfunctions.MoveTowards;

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
	StateList prevStates;
	State curState;
	
	public BehaviorComponent() {
		prevStates = new StateList();
		behaviours.add(new InfectoFrightBehavior()); 
		
		for (int i = 0; i < defaultStates.length; i++) {
			defaultStates[i] = new State();
			defaultStates[i].turnAngle = (float) (2*Math.PI - (i*Math.PI));
		}
		defaultStates[2].turnAngle = 0;
		for (int i = 0; i < defaultStates.length; i++) {
			for (int j = 0; j < defaultStates.length; j++) {
				defaultStates[i].addNextState(defaultStates[j]);
			}
		}
		
		curState = defaultStates[2];
	}
	
	@Override
	public void update(float dt, BaseObject parent) {
		
		calculateProb();
		
		// Update the next available states for the default states
		curState.updateNextStates(dt, parent);
		
		Object[] bObjects = behaviours.getArray();
		int length = behaviours.getCount();
		for (int i = 0; i < length; i++) {
			BehaviorFunction bf = (BehaviorFunction) bObjects[i];
			bf.update(curState.nextStates, probabilities, prevStates);
		}
		
		prevStates.add(curState);
		curState = pickState(curState.nextStates, probabilities);
		
		if(curState == null) return;
		
		((GameObject) parent).pos.set(curState.pos);
		((GameObject) parent).vel.set(curState.vel);
		//((GameObject) parent).direction = curState.angle;
		
		
	}


	private State pickState(FixedSizeArray<State> nextStates, float[] probabilities) {
		float pickState = random.nextFloat();
		float sum = 0f;
		int length = nextStates.getCount();
		for (int i = 0; i < length; i++) {
			sum += probabilities[i];
		}
		
		for (int i = 0; i < length; i++) {
			probabilities[i] /= sum;
			if(i > 0){
				if(i == length -1){
					probabilities[i] = 1f;
					break;
				}
				probabilities[i] += probabilities[i-1];
				
			}
			
		}
		
		State s = null;
		for (int i = 0; i < length; i++) {
			if(pickState <= probabilities[i]) {
				s = nextStates.get(i);
				break;
			}
		}
		
		return s;
	}

	private void calculateProb() {
		int length = curState.nextStates.getCount();
		for(int i = 0;  i < length; i++){
			probabilities[i] = 1.0f;
		}
	}

}
