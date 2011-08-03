package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.FixedSizeArray;

public class ObjectHandler <T extends BaseObject> extends BaseObject 
		implements ObjectHandlerInterface<T>{
	public FixedSizeArray<T> objects;
	
	
	/**
	 * To make sure you can`t add anything during an update loop,
	 * these arrays are applied once before every update! 
	 */
	protected FixedSizeArray<T> pendingRemovals;
	protected FixedSizeArray<T> pendingAdditions;
	
	
	public ObjectHandler(){
		super();
		objects = new FixedSizeArray<T>(DEFAULT_CAPACITY);
		pendingRemovals = new FixedSizeArray<T>(DEFAULT_CAPACITY);
		pendingAdditions = new FixedSizeArray<T>(DEFAULT_CAPACITY);
	}
	
	public ObjectHandler(int size) {
		super();
		objects = new FixedSizeArray<T>(size);
		pendingRemovals = new FixedSizeArray<T>(size);
		pendingAdditions = new FixedSizeArray<T>(size);
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
	public void update(float dt, BaseObject parent){
		
		commitUpdates();
		
		//For speed, we get the raw array. We have to be careful to only read		
		Object[] objectArray = objects.getArray();
		int count = objects.getCount();
		
		for(int i = 0; i < count; i++){
			((BaseObject)objectArray[i]).update(dt, this);
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}

	public  FixedSizeArray<T> getObjects() {
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
