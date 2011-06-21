package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.FixedSizeArray;

public interface ObjectHandlerInterface<T>{
	
	static final int DEFAULT_CAPACITY = 64;
	
	/**
	 * Adds a BaseObject to a pending additions system. When update is called, the
	 * pending additions system will add the pending additions before continuing the
	 * update. 
	 * @param o object to be added.
	 * @throws IllegalObjectException - If there is any requirements 
	 */
	public void add(T o) throws IllegalObjectException;
	public void remove(T o) throws IllegalObjectException;
	public void commitUpdates();
	public void update(float dt, BaseObject parent);
	public FixedSizeArray<T> getObjects();
}
