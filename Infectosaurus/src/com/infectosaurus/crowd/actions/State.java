package com.infectosaurus.crowd.actions;

import com.infectosaurus.BaseObject;
import com.infectosaurus.FixedSizeArray;
import com.infectosaurus.Vector2;

/**
 * 
 *  A State is something that specifies all the
 *   info about the current doing of an agent.
 *  A game object always have one active state. 
 */
public class State extends BaseObject {
	Vector2 pos = new Vector2();
	/**
	 * Facing direction in radians
	 */
	float orientation; 
	
	float time;
	
	//Action action;  Create the class.
	
	FixedSizeArray<State> previousStates;
	
	public State(){ // Has to have this if we want it pooled
		
	}

	@Override
	public void reset() {
		time = 0;
		orientation = 0;
		pos.zero();
		previousStates.clear();
		//Action.reset()
		
	}
	
	
	
}
