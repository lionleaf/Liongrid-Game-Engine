package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.LFixedSizeArray;

public class LObjectHandler <T extends LBaseObject> extends LBaseObject 
		implements LObjectHandlerInterface<T>{
	public LFixedSizeArray<T> objects;
	
	
	/**
	 * To make sure you can`t add anything during an update loop,
	 * these arrays are applied once before every update! 
	 */
	protected LFixedSizeArray<T> pendingRemovals;
	protected LFixedSizeArray<T> pendingAdditions;
	
	
	public LObjectHandler(){
		super();
		objects = new LFixedSizeArray<T>(DEFAULT_CAPACITY);
		pendingRemovals = new LFixedSizeArray<T>(DEFAULT_CAPACITY);
		pendingAdditions = new LFixedSizeArray<T>(DEFAULT_CAPACITY);
	}
	
	public LObjectHandler(int size) {
		super();
		objects = new LFixedSizeArray<T>(size);
		pendingRemovals = new LFixedSizeArray<T>(size);
		pendingAdditions = new LFixedSizeArray<T>(size);
	}

	/**
	 * The given object will be added to and pendingAdditions list. This will not
	 * interfere with the current update list. The commitUpdates method has to 
	 * be called for the additions to be added to the objects array.
	 * @param object
	 */
	public void add(T object){
		pendingAdditions.add(object);
	}
	
	public void remove(T object){
		pendingRemovals.add(object);
	}
	

	public void commitUpdates(){
		final int addCount = pendingAdditions.getCount();
		if(addCount > 0){
			final Object[] additionsArray = pendingAdditions.getArray();
            for (int i = 0; i < addCount; i++) {
                T object = (T) additionsArray[i];
                objects.add(object);
            }
            pendingAdditions.clear();
		}
		
		final int removalCount = pendingRemovals.getCount();
        if (removalCount > 0) {
            final Object[] removalsArray = pendingRemovals.getArray();

            for (int i = 0; i < removalCount; i++) {
                T object = (T)removalsArray[i];
                objects.remove(object, true);
            }
            pendingRemovals.clear();
        }
	}
	
	@Override
	public void update(float dt, LBaseObject parent){
		
		commitUpdates();
		
		//For speed, we get the raw array. We have to be careful to only read		
		Object[] objectArray = objects.getArray();
		int count = objects.getCount();
		
		for(int i = 0; i < count; i++){
			((LBaseObject)objectArray[i]).update(dt, this);
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}

	public  LFixedSizeArray<T> getObjects() {
		return objects;
	}

	public int getCount() {
		return objects.getCount();
	}

	public void clear() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public boolean inArray(T object) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}
