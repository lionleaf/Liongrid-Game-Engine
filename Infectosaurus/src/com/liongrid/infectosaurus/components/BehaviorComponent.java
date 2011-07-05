package com.liongrid.infectosaurus.components;

import java.util.Random;

import android.util.Log;

import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.crowd.State;
import com.liongrid.infectosaurus.crowd.StateList;
import com.liongrid.infectosaurus.crowd.actions.Action;
import com.liongrid.infectosaurus.crowd.actions.Stand;
import com.liongrid.infectosaurus.crowd.actions.Walk;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.AvoidObstacles;
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
	private final static int MAX_STATES = 64;
	private FixedSizeArray<BehaviorFunction> behaviors = 
		new FixedSizeArray<BehaviorFunction>(MAX_BEHAVIOURS);
	private double[] probabilities = new double[MAX_STATES]; 
	private StateList prevStates;
	private State curState;
	
	/**
	 * To track "ownership" changes
	 */
	private InfectoGameObject mLastParent;
	private boolean PRINTPROB = false;
	
	
	public BehaviorComponent() {
		prevStates = new StateList();
		addDefaultBehaviours();	
		curState = new State();
		curState.action = createDefaultActionTree();
	}
	
	
	/**
	 * @return the default starting Action
	 */
	private Action createDefaultActionTree(){
		Action walk = new Walk();
		Action stand = new Stand();
		walk.linkAction(stand,0.05);
		stand.linkAction(walk, 0.07);
		return walk;
	}
	
	
	private void addDefaultBehaviours(){
		addBehaviorFunction(new InfectoFrightBehavior()); 
		addBehaviorFunction(new AvoidObstacles());
	}
	
	
	public void addBehaviorFunction(BehaviorFunction func){
		behaviors.add(func);
	}
	
	@Override
	public void update(float dt, InfectoGameObject parent) {
		
		if(mLastParent != parent){
			curState.pos.set(parent.pos);
			curState.vel.set(parent.vel);
			mLastParent = parent;
		}
		
		
		FixedSizeArray<State> stateChoices = curState.action.getAllNextStates(curState, dt, parent);
		
		calculateDefaultProb(dt, stateChoices, curState.action);
		
		Object[] bObjects = behaviors.getArray();
		int length = behaviors.getCount();
		for (int i = 0; i < length; i++) {
			BehaviorFunction bf = (BehaviorFunction) bObjects[i];
			bf.update(stateChoices, probabilities, prevStates);
		}
		
		prevStates.add(curState);
		
		curState.copy(pickState(stateChoices, probabilities));
		
		if(curState == null) return;
		parent.pos.set(curState.pos);
		parent.vel.set(curState.vel);
		
	}


	private void calculateDefaultProb(float dt, FixedSizeArray<State> stateChoices, Action a) {
		double[] prob = a.getDefaultProbs(dt);
		System.arraycopy(prob, 0, probabilities, 0, stateChoices.getCount());
	}


	private State pickState(FixedSizeArray<State> nextStates, 
							double[] probabilities) {
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
		
		if(PRINTPROB){
			for (int i = 0; i < length; i++) {
				Log.d("Infectosaurus", "State "+i+":"+probabilities[i]+" State "+nextStates.get(i).toString());
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

}
