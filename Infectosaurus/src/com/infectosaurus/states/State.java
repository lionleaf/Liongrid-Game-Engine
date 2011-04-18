package com.infectosaurus.states;

import com.infectosaurus.FixedSizeArray;
import com.infectosaurus.Vector2;

/**
 * 
 *  A State is something that specifies all the
 *   info about the current doing of an agent.
 *  A game object always have one active state. 
 */
public class State {
	Vector2 pos;
	/**
	 * Facing direction in radians
	 */
	float orientation; 
	
	float time;
	
	//Action action;  Create the class.
	
	FixedSizeArray<State> previousStates;
	
}
