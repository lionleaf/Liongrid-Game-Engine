package com.liongrid.infectosaurus.crowd;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.crowd.actions.Action;

/**
 * 
 *  A State is something that specifies all the
 *   info about the current doing of an agent.
 *  A game object always have one active state. 
 */
public class State extends BaseObject{
	public InfectoGameObject parent;
	public Vector2 pos;
	public Vector2 vel;
	public Action action;
		
	public State(){ // Has to have this if we want it pooled
		vel = new Vector2();
		pos = new Vector2();
	}

	@Override
	public void reset() {
		pos.zero();
		vel.zero();
		//Action.reset()
		
	}

	public void copy(State s) {
		this.action = s.action;
		this.vel.set(s.vel);
		this.pos.set(s.pos);
	}

	@Override
	public void update(float dt, BaseObject parent) {}
	
	@Override
	public String toString() {
		return action.toString()+pos.toString()+vel.toString();
	}

	
}
