package com.liongrid.infectosaurus.components;

import java.util.Random;

import android.util.Log;

import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.crowd.State;
import com.liongrid.infectosaurus.crowd.StateList;
import com.liongrid.infectosaurus.crowd.actions.Action;
import com.liongrid.infectosaurus.crowd.actions.Stand;
import com.liongrid.infectosaurus.crowd.actions.Walk;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.AvoidObstacles;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.BehaviorFunction;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.InfectoFrightBehavior;
import com.liongrid.infectosaurus.crowd.situations.Situation;

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
	private final static int MAX_SITUATIONS = 64;
	private FixedSizeArray<BehaviorFunction> behaviors = 
		new FixedSizeArray<BehaviorFunction>(MAX_BEHAVIOURS);
	private FixedSizeArray<Situation> spatialSituations;
	private FixedSizeArray<Situation> nonSpatialSituations;
	private double[] probabilities = new double[MAX_STATES]; 
	private StateList prevStates;
	private State curState;
	/**
	 * To track "ownership" changes
	 */
	private InfectoGameObject mLastParent;
	private boolean PRINTPROB = false;
	
	
	public BehaviorComponent(InfectoGameObject parent) {
		spatialSituations = new FixedSizeArray<Situation>(MAX_SITUATIONS);
		nonSpatialSituations = new FixedSizeArray<Situation>(MAX_SITUATIONS);
		prevStates = new StateList();
		addDefaultBehaviours();	
		curState = new State();
		curState.pos.set(parent.pos);
		curState.action = createDefaultActionTree();
		mLastParent = parent;
	}
	
	
	/**
	 * @return the default starting Action
	 */
	private Action createDefaultActionTree(){
		Action walk = new Walk();
		Action stand = new Stand();
		walk.linkAction(stand,0.0015);
		stand.linkAction(walk, 0.007);
		return walk;
	}
	
	
	private void addDefaultBehaviours(){
		addBehaviorFunction(new InfectoFrightBehavior()); 
		addBehaviorFunction(new AvoidObstacles());
	}
	
	
	public void addBehaviorFunction(BehaviorFunction func){
		behaviors.add(func);
	}
	
	public void removeBehaviorFunction(BehaviorFunction func){
		
		//Most efficient way of removing something from a FixedSizeArray:
		int index = behaviors.find(func, false);
		behaviors.swapWithLast(index);
		behaviors.removeLast();
	}
	
	/**
	 * @return the direct pointer to the spatial situations. Be careful to only read.
	 */
	public FixedSizeArray<Situation> getSpatialSituations(){
		return spatialSituations;
	}
	
	/**
	 * @return the direct pointer to the non-spatial situations. Be careful to only read.
	 */
	public FixedSizeArray<Situation> getNonSpatialSituations(){
		return nonSpatialSituations;
	}
	
	public boolean hasSpatialSituation(Situation situation){
		Object[] array = spatialSituations.getArray();
		int length = spatialSituations.getCount();
		for(int i = 0; i < length; i++){
			if(situation == array[i]) return true;
		}
		return false;
	}
	
	public boolean hasNonSpatialSituation(Situation situation){
		Object[] array = nonSpatialSituations.getArray();
		int length = nonSpatialSituations.getCount();
		for(int i = 0; i < length; i++){
			if(situation == array[i]) return true;
		}
		return false;
	}
	
	@Override
	public void update(float dt, InfectoGameObject parent) {
		
		if(mLastParent != parent){
			curState.pos.set(parent.pos);
			curState.vel.set(parent.mVel);
			mLastParent = parent;
		}
		
		GameActivity.infectoPointers.situationHandler.updateSituations(parent, this);
		
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
		parent.mVel.set(curState.vel);
		if(parent.spriteComponent != null){
			parent.spriteComponent.setUnderlyingAnimation(curState.action.toString());
		}
		
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


	public void addSpatialSituation(Situation situation) {
			spatialSituations.add(situation);
	}
	
	public void addNonSpatialSituation(Situation situation){
		nonSpatialSituations.add(situation);
	}

	public void removeSpatialSituation(Situation situation){
		spatialSituations.remove(situation, true);
	}
	public void removeNonSpatialSituation(Situation situation) {
			nonSpatialSituations.remove(situation, true);
	}

}
