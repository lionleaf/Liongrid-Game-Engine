package com.infectosaurus.crowd;

import com.infectosaurus.BaseObject;
import com.infectosaurus.FixedSizeArray;
import com.infectosaurus.GameObject;
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
	static final int MAX_STATES = 5;
	FixedSizeArray<State> previousStates;
	public FixedSizeArray<State> nextStates 
		= new FixedSizeArray<State>(MAX_STATES);
	public float turnAngle = 0;
	
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
	
	@Override
	public void update(float dt, BaseObject parent){
		Vector2 vel = ((GameObject) parent).vel;
		if (turnAngle != 0){
			// Angle to turn
			float angle = dt*turnAngle;
			vel.x = (float) (vel.x * Math.cos(angle) - vel.y * Math.cos(angle));
			vel.y = (float) (vel.x * Math.sin(angle) + vel.y * Math.sin(angle));
		}
		Vector2 pos = ((GameObject) parent).pos;
		pos.add(vel.x * dt, vel.y * dt);
	}

	public void addNextState(State state) {
		nextStates.add(state);
	}
	
	public void removeState(State state){
		nextStates.remove(state, true);
	}

	
}
