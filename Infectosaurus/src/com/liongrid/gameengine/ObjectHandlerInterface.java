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
	public void add(T o) throws UnsupportedOperationException;
	public void remove(T o) throws UnsupportedOperationException;
	public void commitUpdates();
	public void update(float dt, BaseObject parent);
	public int getCount() throws UnsupportedOperationException;
	public FixedSizeArray<T> getObjects() throws UnsupportedOperationException;
	public void clear() throws UnsupportedOperationException;
}
