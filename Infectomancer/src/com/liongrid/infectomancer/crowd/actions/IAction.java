package com.liongrid.infectomancer.crowd.actions;

import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.infectomancer.IGameObject;
import com.liongrid.infectomancer.crowd.IState;

public abstract class IAction {
	private final static int MAX_LINKS = 32;
	protected final static int MAX_STATES = MAX_LINKS * 2; 
	private LFixedSizeArray<IAction> mLinkedActions = new LFixedSizeArray<IAction>(MAX_LINKS);
	private LFixedSizeArray<IState> mStateList = new LFixedSizeArray<IState>(MAX_STATES);
	protected double[] mDefaultLinkedProb = new double[MAX_LINKS];
	protected double[] mDefaultProbs = new double[MAX_STATES];
	protected int mStateNumber;
	
	protected LFixedSizeArray<IState> stateList = new LFixedSizeArray<IState>(MAX_STATES);
	protected String mName;
	
	
	public void linkAction(IAction a, double chance){
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
			
			int nrOfStates  = ((IAction)rawarr[i]).mStateNumber;
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
	
	public LFixedSizeArray<IState> getAllNextStates
							(IState lastState, float dt, IGameObject parent){
		
		mStateList.clearWithoutReleasing();
	
		int l = mLinkedActions.getCount();
		Object[] rawArr = mLinkedActions.getArray();
		for (int i = 0; i < l; i++) {
			mStateList.append(((IAction)rawArr[i]).
					getInternalNextStates(lastState, dt, parent));
		}
		
		mStateList.append(getInternalNextStates(lastState, dt, parent));
		
		return mStateList;
	}
	
	@Override
	public String toString() {
		return mName;
	}

	public abstract LFixedSizeArray<IState> getInternalNextStates
							(IState lastState, float dt, IGameObject parent);

	public String getName() {
		return mName;
	}
	
	public abstract String getAnimationCode(IState state);


}
