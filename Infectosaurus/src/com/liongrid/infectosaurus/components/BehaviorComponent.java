package com.liongrid.infectosaurus.components;

import java.util.Random;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.GameObject;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.crowd.State;
import com.liongrid.infectosaurus.crowd.StateList;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.AvoidEdgeBehaviour;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.AvoidObstacles;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.AvoidTargetCollission;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.BehaviorFunction;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.InfectoFrightBehavior;

/**
 * @author lastis
 * The behavior component makes an game object able to initiate a state
 * and chooses a number from 0 to 1 to initiate a specific state.
 * The probability of each of the states are modified by the behavior
 * functions. 
 */
public class BehaviorComponent extends Component<InfectoGameObject>{
	
	static Random random = new Random();
	public static final int DEFAULT_STATES = 5;
	private final static int MAX_BEHAVIOURS = 32;
	FixedSizeArray<BehaviorFunction> behaviors = 
		new FixedSizeArray<BehaviorFunction>(MAX_BEHAVIOURS);
	float[] probabilities = new float[MAX_BEHAVIOURS]; 
	State[] defaultStates = new State[DEFAULT_STATES];
	StateList prevStates;
	State curState;
	
	public BehaviorComponent() {
		prevStates = new StateList();
		addDefaultBehaviours();
		
		for (int i = 0; i < defaultStates.length; i++) {
			defaultStates[i] = new State();
			defaultStates[i].turnAngle = (float) (2*Math.PI - (i*Math.PI));
		}
		defaultStates[2].turnAngle = 0;
		// Each of the default states has a pointer to each other in nextStates.
		for (int i = 0; i < defaultStates.length; i++) {
			for (int j = 0; j < defaultStates.length; j++) {
				defaultStates[i].addNextState(defaultStates[j]);
			}
		}
		curState = defaultStates[2];
	}
	
	
	private void addDefaultBehaviours(){
		behaviors.add(new InfectoFrightBehavior()); 
		behaviors.add(new AvoidObstacles());
//		behaviors.add(new AvoidTargetCollission());
	}
	
	
	public void addBehaviorFunction(BehaviorFunction func){
		behaviors.add(func);
	}
	
	@Override
	public void update(float dt, InfectoGameObject parent) {
		
		calculateProb();
		
		// Update the next available states for the default states
		curState.updateNextStates(dt, parent);
		
		Object[] bObjects = behaviors.getArray();
		int length = behaviors.getCount();
		for (int i = 0; i < length; i++) {
			BehaviorFunction bf = (BehaviorFunction) bObjects[i];
			bf.update(curState.nextStates, probabilities, prevStates);
		}
		
		prevStates.add(curState);
		curState = pickState(curState.nextStates, probabilities);
		
		if(curState == null) return;
		
		parent.pos.set(curState.pos);
		parent.vel.set(curState.vel);
		//((GameObject) parent).direction = curState.angle;
		
	}


	private State pickState(FixedSizeArray<State> nextStates, 
							float[] probabilities) {
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
