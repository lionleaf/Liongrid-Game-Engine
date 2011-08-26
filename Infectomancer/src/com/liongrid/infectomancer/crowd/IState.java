package com.liongrid.infectomancer.crowd;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.tools.LVector2;
import com.liongrid.infectomancer.IGameObject;
import com.liongrid.infectomancer.crowd.actions.IAction;

/**
 * 
 *  A IState is something that specifies all the
 *   info about the current doing of an agent.
 *  A game object always have one active state. 
 */
public class IState extends LBaseObject{
	public IGameObject parent;
	public LVector2 pos;
	public LVector2 vel;
	public IAction action;
		
	public IState(){ // Has to have this if we want it pooled
		vel = new LVector2();
		pos = new LVector2();
	}

	@Override
	public void reset() {
		pos.zero();
		vel.zero();
		//IAction.reset()
		
	}

	public void copy(IState s) {
		this.action = s.action;
		this.vel.set(s.vel);
		this.pos.set(s.pos);
	}

	@Override
	public void update(float dt, LBaseObject parent) {}
	
	@Override
	public String toString() {
		return action.toString()+pos.toString()+vel.toString();
	}

	
}
