package com.infectosaurus.crowd;

import com.infectosaurus.tools.ObjectPool;

public class StateList {
	public static final int LIST_SIZE = 10;
	private State[] list;
	private int index;
	
	public StateList(){
		list = new State[LIST_SIZE];
		for(int i = 0 ; i < list.length; i++){
			list[i] = new State();
		}
	}
	
	public void add(State s){
		list[index].reset();
		list[index].copy(s);
		if(index != LIST_SIZE - 1) index += 1;
		else index = 0;
		
	}
	
	/**
	 * @param prev Number of steps back in the list
	 * @return the State at "prev" states back
	 */
	public State get(int prev){
		int i;
		if(index - prev < 0) i = LIST_SIZE + (index - prev);
		else i = index - prev;
		return list[i];
	}

}
