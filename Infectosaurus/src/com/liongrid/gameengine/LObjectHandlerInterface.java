package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.LFixedSizeArray;

public interface LObjectHandlerInterface<T>{
	
	static final int DEFAULT_CAPACITY = 64;
	
	public void commitUpdates();
	public void update(float dt, LBaseObject parent);
	/**
	 * Adds a LBaseObject to a pending additions system. When update is called, the
	 * pending additions system will add the pending additions before continuing the
	 * update. 
	 * @param o object to be added.
	 * @throws LIllegalObjectException - If there is any requirements 
	 */
	public void add(T o) throws UnsupportedOperationException;
	public void remove(T o) throws UnsupportedOperationException;
	public int getCount() throws UnsupportedOperationException;
	public void clear() throws UnsupportedOperationException;
	public boolean inArray(T object) throws UnsupportedOperationException;
}
