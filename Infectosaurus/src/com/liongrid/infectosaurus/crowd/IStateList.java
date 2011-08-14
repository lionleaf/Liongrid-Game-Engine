package com.liongrid.infectosaurus.crowd;

public class IStateList {
	public static final int LIST_SIZE = 10;
	private IState[] list;
	private int index = 0;
	
	/**
	 * A list of states that reuses the state via .reset() and .copy().
	 * The length of the list = LIST_SIZE. 
	 */
	public IStateList(){
		list = new IState[LIST_SIZE];
		for(int i = 0 ; i < list.length; i++){
			list[i] = new IState();
		}
	}
	
	/**
	 * Add a state to the state list. This method will use use the .reset() and .copy()
	 * of a state to copy it to the list. This is to reuse the objects.
	 * @param s state to be added
	 */
	public void add(IState s){
		list[index].reset();
		list[index].copy(s);
		if(index != LIST_SIZE - 1) index += 1;
		else index = 0;
		
	}
	
	/**
	 * @param prev Number of steps back in the list. If prev is greater than list size,
	 * the index will go to back to the start. 
	 * @return the IState at "prev" states back
	 */
	public IState get(int prev){
		int i;
		if(index - prev < 0) i = LIST_SIZE + (index - prev);
		else i = index - prev;
		return list[i];
	}

}
