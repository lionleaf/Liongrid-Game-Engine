package com.infectosaurus;

public class ObjectHandler <T extends BaseObject> extends BaseObject {
	protected FixedSizeArray<T> objects;
	
	
	/**
	 * To make sure you can`t add anything during an update loop,
	 * these arrays are applied once before every update! 
	 */
	protected FixedSizeArray<T> pendingRemovals;
	protected FixedSizeArray<T> pendingAdditions;
	
	private static final int DEFAULT_CAPACITY = 64;
	String tag = "ObjectHandler";
	
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

	public FixedSizeArray<T> getObjects() {
		return objects;
	}

}
