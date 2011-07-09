package com.liongrid.infectosaurus.crowd.actions;

import java.util.Arrays;

import android.util.Log;

import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.crowd.State;

public abstract class Action {
	private final static int MAX_LINKS = 32;
	protected final static int MAX_STATES = MAX_LINKS * 2; 
	private FixedSizeArray<Action> mLinkedActions = new FixedSizeArray<Action>(MAX_LINKS);
	private FixedSizeArray<State> mStateList = new FixedSizeArray<State>(MAX_STATES);
	protected double[] mDefaultLinkedProb = new double[MAX_LINKS];
	protected double[] mDefaultProbs = new double[MAX_STATES];
	protected int mStateNumber;
	
	protected FixedSizeArray<State> stateList = new FixedSizeArray<State>(MAX_STATES);
	protected String mName;
	
	
	public void linkAction(Action a, double chance){
		int currentCnt = mLinkedActions.getCount();
		mLinkedActions.add(a);
		mDefaultLinkedProb[currentCnt] = chance;
		mStateNumber = getNumberOfStates();
	}
	
	public abstract int getNumberOfStates();
	
	/**
	 * @param dt
	 * @return 
	 * WARNING: Do not point to this array! Only copy it!
	 */
	public double[] getDefaultProbs(float dt){
		int cIndex = 0;
		double totalChangeProb = 0;
		
		int l = mLinkedActions.getCount();
		Object[] rawarr =  mLinkedActions.getArray();
		for (int i = 0; i < l; i++) {
			
			int nrOfStates  = ((Action)rawarr[i]).mStateNumber;
			double prob = mDefaultLinkedProb[i];
			
			totalChangeProb += prob;
			//Do the probability math, to make it prob % chance per sec to chance to action
			prob /= nrOfStates; 
			prob = 1 - Math.pow(1 - prob, dt);
			
			
			
			
			for (int j = 0; j < nrOfStates;j++) {
				mDefaultProbs[cIndex++] = prob;
			}
		}
		
		for (int i = 0; i < mStateNumber; i++) {
			 double prob = (1 - totalChangeProb)/mStateNumber;
			 mDefaultProbs[cIndex++] = 1 - Math.pow(1-prob,dt);
		}
		return mDefaultProbs;
	}
	
	public FixedSizeArray<State> getAllNextStates
							(State lastState, float dt, InfectoGameObject parent){
		
		mStateList.clearWithoutReleasing();
	
		int l = mLinkedActions.getCount();
		Object[] rawArr = mLinkedActions.getArray();
		for (int i = 0; i < l; i++) {
			mStateList.append(((Action)rawArr[i]).
					getInternalNextStates(lastState, dt, parent));
		}
		
		mStateList.append(getInternalNextStates(lastState, dt, parent));
		
		return mStateList;
	}
	@Override
	public String toString() {
		return mName;
	}

	public abstract FixedSizeArray<State> getInternalNextStates
							(State lastState, float dt, InfectoGameObject parent);


}
