package com.infectosaurus;

public class ObjectHandler extends BaseObject {
	protected FixedSizeArray<BaseObject> objects;
	private static final int DEFAULT_CAPACITY = 64;
	String tag = "ObjectHandler";
	
	public ObjectHandler(){
		super();
		objects = new FixedSizeArray<BaseObject>(DEFAULT_CAPACITY);
	}
	
	public void add(BaseObject object){
		objects.add(object);
	}
	
	@Override
	public void update(float dt, BaseObject parent){
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
