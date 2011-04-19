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
	public Vector2 pos = new Vector2();
	public Vector2 vel;
	float time;
	public float turnAngle = 0;
	
	//Action action;  Create the class.
	static final int MAX_STATES = 5;
	FixedSizeArray<State> previousStates;
	public FixedSizeArray<State> nextStates 
		= new FixedSizeArray<State>(MAX_STATES);
	
	public State(){ // Has to have this if we want it pooled
		vel = new Vector2();
		pos = new Vector2();
	}

	@Override
	public void reset() {
		time = 0;
		turnAngle = 0;
		pos.zero();
		vel.zero();
		previousStates.clear();
		//Action.reset()
		
	}
	
	@Override
	public void update(float dt, BaseObject parent){
		if (turnAngle != 0){
			// Angle to turn
			float angle = dt*turnAngle;
			vel.x = (float) (vel.x * Math.cos(angle) - vel.y * Math.cos(angle));
			vel.y = (float) (vel.x * Math.sin(angle) + vel.y * Math.sin(angle));
			((GameObject) parent).vel = vel;
		}
		pos = ((GameObject) parent).pos;
		pos.add(vel.x * dt, vel.y * dt);
	}

	public void updateNextStates(float dt, BaseObject parent){
		Object[] states = nextStates.getArray();
		int length = nextStates.getCount();
		for (int i = 0; i < length; i++) {
			State s = (State) states[i];
			s.update(dt, parent);
		}
	}
	
	public void addNextState(State state) {
		nextStates.add(state);
	}
	
	public void removeState(State state){
		nextStates.remove(state, true);
	}

	
}
