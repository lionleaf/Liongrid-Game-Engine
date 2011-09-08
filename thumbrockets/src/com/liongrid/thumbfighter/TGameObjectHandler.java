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
		mCollisionHandler.add(object.hitBox);
	}
	
	@Override
	public void update(float dt, LBaseObject parent) {
		super.update(dt, parent);
		mCollisionHandler.update(dt, this);
	}
	
	

}
