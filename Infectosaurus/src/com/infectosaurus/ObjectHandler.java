package com.infectosaurus;

public class ObjectHandler extends BaseObject {
	protected FixedSizeArray<BaseObject> objects;
	
	
	/**
	 * To make sure you can`t add anything during an update loop,
	 * these arrays are applied once before every update! 
	 */
	protected FixedSizeArray<BaseObject> pendingRemovals;
	protected FixedSizeArray<BaseObject> pendingAdditions;
	
	private static final int DEFAULT_CAPACITY = 64;
	String tag = "ObjectHandler";
	
	public ObjectHandler(){
		super();
		objects = new FixedSizeArray<BaseObject>(DEFAULT_CAPACITY);
		pendingRemovals = new FixedSizeArray<BaseObject>(DEFAULT_CAPACITY);
		pendingAdditions = new FixedSizeArray<BaseObject>(DEFAULT_CAPACITY);
	}
	
	public ObjectHandler(int size) {
		super();
		objects = new FixedSizeArray<BaseObject>(size);
		pendingRemovals = new FixedSizeArray<BaseObject>(size);
		pendingAdditions = new FixedSizeArray<BaseObject>(size);
	}

	public void add(BaseObject object){
		pendingAdditions.add(object);
	}
	
	public void remove(BaseObject object){
		pendingRemovals.add(object);
	}
	

	public void commitUpdates(){
		final int addCount = pendingAdditions.getCount();
		if(addCount > 0){
			final Object[] additionsArray = pendingAdditions.getArray();
            for (int i = 0; i < addCount; i++) {
                BaseObject object = (BaseObject)additionsArray[i];
                objects.add(object);
            }
            pendingAdditions.clear();
		}
		
		final int removalCount = pendingRemovals.getCount();
        if (removalCount > 0) {
            final Object[] removalsArray = pendingRemovals.getArray();

            for (int i = 0; i < removalCount; i++) {
                BaseObject object = (BaseObject)removalsArray[i];
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

	public FixedSizeArray<BaseObject> getObjects() {
		return objects;
	}
	

}
