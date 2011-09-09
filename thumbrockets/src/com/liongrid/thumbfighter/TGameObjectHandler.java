package com.liongrid.thumbfighter;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCollisionHandler;
import com.liongrid.gameengine.LObjectHandler;

public class TGameObjectHandler extends LObjectHandler<TGameObject> {
	
	private LCollisionHandler mCollisionHandler;
	private final int MAX_COLLISION_OBJECTS = 256;
	public TGameObjectHandler() {
		mCollisionHandler = new LCollisionHandler(256);
	}
	
	@Override
	public void add(TGameObject object) {
		super.add(object);
		
	}
	
	@Override
	public void remove(TGameObject object) {
		super.remove(object);
	}
	
	@Override
	public void update(float dt, LBaseObject parent) {
		super.update(dt, parent);
		mCollisionHandler.clear();
		addAllToCollision();
		mCollisionHandler.update(dt, this);
	}
	
	private void addAllToCollision(){
		Object[] rawarr = objects.getArray();
		int len = objects.getCount();
		for (int i = 0; i < len; i++) {
			TGameObject gObject = (TGameObject) rawarr[i];
			mCollisionHandler.add(gObject.hitBox);
		}
	}
	
	

}
