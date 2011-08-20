package com.liongrid.infectosaurus.components;

import java.util.Random;

import android.util.Log;

import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.infectosaurus.IGameObject;
import com.liongrid.infectosaurus.IGamePointers;
import com.liongrid.infectosaurus.crowd.IState;
import com.liongrid.infectosaurus.crowd.IStateList;
import com.liongrid.infectosaurus.crowd.actions.IAction;
import com.liongrid.infectosaurus.crowd.actions.IStand;
import com.liongrid.infectosaurus.crowd.actions.IWalk;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.IAvoidObstacles;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.IBehaviorFunction;
import com.liongrid.infectosaurus.crowd.behaviorfunctions.IFrightBehavior;
import com.liongrid.infectosaurus.crowd.situations.ISituation;

/**
 * @author lastis
 * The behavior component makes an game object able to initiate a state
 * and chooses a number from 0 to 1 to initiate a specific state.
 * The probability of each of the states are modified by the behavior
 * functions. 
 */
public class IBehaviorComponent extends LComponent<IGameObject>{
	
	static Random random = new Random();
	public static final int DEFAULT_STATES = 5;
	private final static int MAX_BEHAVIOURS = 32;
	private final static int MAX_STATES = 64;
	private final static int MAX_SITUATIONS = 64;
	private LFixedSizeArray<IBehaviorFunction> behaviors = 
		new LFixedSizeArray<IBehaviorFunction>(MAX_BEHAVIOURS);
	private LFixedSizeArray<ISituation> spatialSituations;
	private LFixedSizeArray<ISituation> nonSpatialSituations;
	private double[] probabilities = new double[MAX_STATES]; 
	private IStateList prevStates;
	private IState curState;
	/**
	 * To track "ownership" changes
	 */
	private IGameObject mLastParent;
	private boolean PRINTPROB = false;
	
	
	public IBehaviorComponent(IGameObject parent) {
		spatialSituations = new LFixedSizeArray<ISituation>(MAX_SITUATIONS);
		nonSpatialSituations = new LFixedSizeArray<ISituation>(MAX_SITUATIONS);
		prevStates = new IStateList();
		addDefaultBehaviours();	
		curState = new IState();
		curState.pos.set(parent.mPos);
		curState.action = createDefaultActionTree();
		mLastParent = parent;
	}
	
	
	/**
	 * @return the default starting IAction
	 */
	private IAction createDefaultActionTree(){
		IAction walk = new IWalk();
		IAction stand = new IStand();
		walk.linkAction(stand,0.0015);
		stand.linkAction(walk, 0.007);
		return walk;
	}
	
	
	private void addDefaultBehaviours(){
		addBehaviorFunction(new IFrightBehavior()); 
		addBehaviorFunction(new IAvoidObstacles());
	}
	
	
	public void addBehaviorFunction(IBehaviorFunction func){
		behaviors.add(func);
	}
	
	public void removeBehaviorFunction(IBehaviorFunction func){
		
		//Most efficient way of removing something from a LFixedSizeArray:
		int index = behaviors.find(func, false);
		behaviors.swapWithLast(index);
		behaviors.removeLast();
	}
	
	/**
	 * @return the direct pointer to the spatial situations. Be careful to only read.
	 */
	public LFixedSizeArray<ISituation> getSpatialSituations(){
		return spatialSituations;
	}
	
	/**
	 * @return the direct pointer to the non-spatial situations. Be careful to only read.
	 */
	public LFixedSizeArray<ISituation> getNonSpatialSituations(){
		return nonSpatialSituations;
	}
	
	public boolean hasSpatialSituation(ISituation situation){
		Object[] array = spatialSituations.getArray();
		int length = spatialSituations.getCount();
		for(int i = 0; i < length; i++){
			if(situation == array[i]) return true;
		}
		return false;
	}
	
	public boolean hasNonSpatialSituation(ISituation situation){
		Object[] array = nonSpatialSituations.getArray();
		int length = nonSpatialSituations.getCount();
		for(int i = 0; i < length; i++){
			if(situation == array[i]) return true;
		}
		return false;
	}
	
	@Override
	public void update(float dt, IGameObject parent) {
		
		if(mLastParent != parent){
			curState.pos.set(parent.mPos);
			curState.vel.set(parent.mVel);
			mLastParent = parent;
		}
		
		IGamePointers.situationHandler.updateSituations(parent, this);
		
		LFixedSizeArray<IState> stateChoices = curState.action.getAllNextStates(curState, dt, parent);
		
		calculateDefaultProb(dt, stateChoices, curState.action);
		
		Object[] bObjects = behaviors.getArray();
		int length = behaviors.getCount();
		for (int i = 0; i < length; i++) {
			IBehaviorFunction bf = (IBehaviorFunction) bObjects[i];
			bf.update(stateChoices, probabilities, prevStates);
		}
		
		prevStates.add(curState);
		
		curState.copy(pickState(stateChoices, probabilities));
		
		if(curState == null) return;
		parent.mPos.set(curState.pos);
		parent.mVel.set(curState.vel);
		if(parent.spriteComponent != null){
			parent.spriteComponent.
			 setUnderlyingAnimation(curState.action.getAnimationCode(curState));
		}
	}


	private void calculateDefaultProb(float dt, LFixedSizeArray<IState> stateChoices, IAction a) {
		double[] prob = a.getDefaultProbs(dt);
		System.arraycopy(prob, 0, probabilities, 0, stateChoices.getCount());
	}


	private IState pickState(LFixedSizeArray<IState> nextStates, 
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
				Log.d("Infectosaurus", "IState "+i+":"+probabilities[i]+" IState "+nextStates.get(i).toString());
			}
		}
		
		IState s = null;
		for (int i = 0; i < length; i++) {
			if(pickState <= probabilities[i]) {
				s = nextStates.get(i);
				break;
			}
		}
		
		return s;
	}

	public void addSpatialSituation(ISituation situation) {
			spatialSituations.add(situation);
	}
	
	public void addNonSpatialSituation(ISituation situation){
		nonSpatialSituations.add(situation);
	}

	public void removeSpatialSituation(ISituation situation){
		spatialSituations.remove(situation, true);
	}
	public void removeNonSpatialSituation(ISituation situation) {
			nonSpatialSituations.remove(situation, true);
	}

}
