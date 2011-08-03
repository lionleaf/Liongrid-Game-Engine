package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.gameengine.tools.Vector2;

public class CollisionHandler extends BaseObject 
		implements ObjectHandlerInterface<CollisionObject>{
	
	public static final int TYPE_LESS = -1;
	public static final int DEFAULT_TYPELESS_CAPACITY = 5;
	
	private CollisionObject[] collisionObjects;
	private CollisionObject[] typeLess;
	private int collisionObjectCnt;
	private int typeLessCnt;
	private FixedSizeArray<CollisionObject> pendingAdditions;
	
	
	public CollisionHandler(int capacity) {
		pendingAdditions = new FixedSizeArray<CollisionObject>(capacity);
		typeLess = new CollisionObject[DEFAULT_TYPELESS_CAPACITY];
		collisionObjects = new CollisionObject[capacity];
	}

	public void add(CollisionObject o){
		pendingAdditions.add(o);
	}

	public void remove(CollisionObject o) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}

	public void commitUpdates() {
		CollisionObject collisionObject;
		Object[] rawArr = pendingAdditions.getArray();
		int length = pendingAdditions.getCount();
		for(int i = 0; i < length; i++){
			collisionObject = (CollisionObject) rawArr[i];
			if(collisionObject.getType() == TYPE_LESS){
				typeLess[typeLessCnt] = collisionObject;
				typeLessCnt += 1;
				continue;
			}
			collisionObjects[collisionObjectCnt] = collisionObject;
			collisionObjectCnt += 1;
		}
		pendingAdditions.clear();
	}

	public FixedSizeArray<CollisionObject> getObjects() 
			throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	

	@Override
	public void update(float dt, BaseObject parent) {
		commitUpdates();
		clearCollisionObjects();
		
		if(typeLessCnt == 0){
			for(int i = 0; i < collisionObjectCnt; i++){
				collides(i, dt);
			}
		}
		
		else{
			for(int i = 0; i < collisionObjectCnt; i++){
				for(int j = 0; j < typeLessCnt; j++){
					typeLess[j].collide(collisionObjects[i]);
				}
				collides(i, dt);
			}
		}
	}
	

	private void collides(int index, float dt) {
		CollisionObject shape1 = collisionObjects[index];
		CollisionObject shape2;
		
		for(int i = index + 1; i < collisionObjectCnt; i++){
			shape2 = collisionObjects[i];
			shape1.collide(shape2);
			shape2.collide(shape1);
		}
	}

	private void clearCollisionObjects() {
		for(int i = 0; i < collisionObjectCnt; i++){
			collisionObjects[i].clear();
		}
	}
	
	/**
	 * @param pos - the position
	 * @param type - the type of the objects to return
	 * @return The closest object. Returns Float.MAX_VALUE if no one is found
	 */
	public CollisionObject getClosest(Vector2 pos, int type) {
		CollisionObject returnO = null;
		int i; float closestDistance = Float.MAX_VALUE;
		for(i = 0; i < collisionObjectCnt; i++){
			if(collisionObjects[i].getType() != type) continue;
			float distance = pos.distance2(collisionObjects[i].getPos());
			if(distance < closestDistance) {
				closestDistance = distance;
				returnO = collisionObjects[i];
			}
		}
		return returnO;
	}
	
	/**
	 * @param pos - the position
	 * @param types - the types of the objects to return
	 * @return The closest object. Returns Float.MAX_VALUE if no one is found
	 */
	public CollisionObject getClosest(Vector2 pos, int[] types){
		CollisionObject returnO = null;
		float closestDistance = Float.MAX_VALUE; int type; int i;
		for(int typeIndex = 0; typeIndex < types.length; typeIndex ++){
			type = types[typeIndex];
			for(i = 0; i < collisionObjectCnt; i++){
				if(collisionObjects[typeIndex].getType() != type) continue;
				float distance = pos.distance2(collisionObjects[i].getPos());
				if(distance < closestDistance) {
					closestDistance = distance;
					returnO = collisionObjects[i];
				}
			}
		}
		return returnO;
	}
	
	/**
	 * @param pos - position
	 * @param withIn - Get all objects within this distance
	 * @param types - The types of the objects to be search for
	 * @param array - An array to copy the objects found to.
	 * @return array
	 */
	public CollisionObject[] getClose(Vector2 pos, float withIn, 
			int[] types, CollisionObject[] array){
		
		int type; int count = 0; float dis2;
		for(int typeIndex = 0; typeIndex < types.length; typeIndex ++){
			type = types[typeIndex];
			for(int i = 0; i < collisionObjectCnt; i++){
				if(count >= array.length) return array;
				if(collisionObjects[i].getType() != type) continue;
				dis2 = pos.distance2(collisionObjects[i].getPos());
				if(dis2 < withIn * withIn){
					array[count] = collisionObjects[i];
					count ++;
				}
			}
		}
		return array;
	}
	
	/**
	 * @param pos - position
	 * @param withIn - Get all objects within this distance
	 * @param type - The types of the objects to be search for
	 * @param array - An array to copy the objects found to.
	 * @return array
	 */
	public CollisionObject[] getClose(Vector2 pos, float withIn, 
			int type, CollisionObject[] array){
		
		int count = 0; float dis2;
		for(int i = 0; i < collisionObjectCnt; i++){
			if(collisionObjects[i].getType() != type) continue;
			if(count >= array.length) return array;
			dis2 = pos.distance2(collisionObjects[i].getPos());
			if(dis2 < withIn * withIn){
				array[count] = collisionObjects[i];
				count ++;
			}
		}
		return array;
	}
			

	@Override
	public void reset() {
		
	}


	public int getCount() {
		return collisionObjectCnt;
	}
	
	public int getCount(int type){
		int count = 0;
		for(int i = 0; i < collisionObjectCnt; i++){
			if(collisionObjects[i].getType() == type) count++;
		}
		return count;
	}


	public void clear(){
		collisionObjectCnt = 0;
		typeLessCnt = 0;
	}

	public boolean inArray(CollisionObject object) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}