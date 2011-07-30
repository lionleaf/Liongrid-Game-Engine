package com.liongrid.gameengine;

import android.R.color;
import android.util.Log;

import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.Main;

/**
 * @author Lastis
 */
public class CollisionHandlerMultipleArrays extends BaseObject 
		implements ObjectHandlerInterface<CollisionObject>{
	
	public static final int TYPE_LESS = -1;
	public static final int DEFAULT_TYPELESS_CAPACITY = 5;
	
	private CollisionObject[][] collisionObjects;
	private CollisionObject[] typeLess;
	private int[] collisionObjectCnt;
	private int typeLessCnt;
	private FixedSizeArray<CollisionObject> pendingAdditions;
	
	
	public CollisionHandlerMultipleArrays(int typeCnt, int capacity) {
		pendingAdditions = new FixedSizeArray<CollisionObject>(capacity);
		typeLess = new CollisionObject[DEFAULT_TYPELESS_CAPACITY];
		typeLessCnt = 0;
		
		collisionObjects = new CollisionObject[typeCnt][];
		for(int i = 0; i < collisionObjects.length; i++){
			collisionObjects[i] = new CollisionObject[DEFAULT_CAPACITY];
		}
		
		collisionObjectCnt = new int[typeCnt];
		for(int i = 0; i < collisionObjectCnt.length; i++){
			collisionObjectCnt[i] = 0;
		}
	}

	public void add(CollisionObject o){
		pendingAdditions.add(o);
	}

	public void remove(CollisionObject o) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}

	public void commitUpdates() {
		int type;
		CollisionObject collisionObject;
		Object[] rawArr = pendingAdditions.getArray();
		int length = pendingAdditions.getCount();
		for(int i = 0; i < length; i++){
			collisionObject = (CollisionObject) rawArr[i];
			type = collisionObject.getType();
			if(type == TYPE_LESS){
				typeLess[typeLessCnt] = collisionObject;
				typeLessCnt += 1;
				continue;
			}
			int index = collisionObjectCnt[type];
			collisionObjects[type][index] = collisionObject;
			collisionObjectCnt[type] += 1;
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
			for(int i = 0; i < collisionObjectCnt.length; i++){
				for(int j = 0; j < collisionObjectCnt[i]; j++){
					collides(i, j, dt);
				}
			}
		}
		
		else{
			for(int i = 0; i < collisionObjectCnt.length; i++){
				for(int j = 0; j < collisionObjectCnt[i]; j++){
					for(int k = 0; k < typeLessCnt; k++){
						typeLess[k].collide(collisionObjects[i][j]);
					}
					collides(i, j, dt);
				}
			}
		}
	}
	

	private void collides(int typeIndex, int shapeIndex, float dt) {
		CollisionObject shape1 = collisionObjects[typeIndex][shapeIndex];
		CollisionObject shape2;
		
		for(int i = typeIndex; i < collisionObjectCnt.length; i++){
			for(int j = i == typeIndex ? shapeIndex + 1 : 0; j < collisionObjectCnt[i]; j++){
				shape2 = collisionObjects[i][j];
				shape1.collide(shape2);
				shape2.collide(shape1);
			}
		}
	}

	private void clearCollisionObjects() {
		for(int i = 0; i < collisionObjectCnt.length ; i++){
			for(int j = 0; j < collisionObjectCnt[i]; j++){
				collisionObjects[i][j].clear();
			}
		}
	}
	
	/**
	 * @param pos - the position
	 * @param type - the type of the objects to return
	 * @return The closest object. Returns Float.MAX_VALUE if no one is found
	 */
	public CollisionObject getClosest(Vector2 pos, int type) {
		CollisionObject returnO = null;
		int index; float closestDistance = Float.MAX_VALUE;
		for(index = 0; index < collisionObjectCnt[type]; index++){
			float distance = pos.distance2(collisionObjects[type][index].getPos());
			if(distance < closestDistance) {
				closestDistance = distance;
				returnO = collisionObjects[type][index];
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
		float closestDistance = Float.MAX_VALUE; int type; int index;
		for(int i = 0; i < types.length; i ++){
			type = types[i];
			for(index = 0; index < collisionObjectCnt[type]; index++){
				float distance = pos.distance2(collisionObjects[type][index].getPos());
				if(distance < closestDistance) {
					closestDistance = distance;
					returnO = collisionObjects[type][index];
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
		for(int i = 0; i < types.length; i ++){
			type = types[i];
			for(int index = 0; index < collisionObjectCnt[type]; index++){
				if(count >= array.length) return array;
				dis2 = pos.distance2(collisionObjects[type][index].getPos());
				if(dis2 < withIn * withIn){
					array[count] = collisionObjects[type][index];
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
		for(int index = 0; index < collisionObjectCnt[type]; index++){
			if(count >= array.length) return array;
			dis2 = pos.distance2(collisionObjects[type][index].getPos());
			if(dis2 < withIn * withIn){
				array[count] = collisionObjects[type][index];
				count ++;
			}
		}
		return array;
	}
			

	@Override
	public void reset() {
		
	}


	public int getCount() {
		int cnt = 0;
		for (int i = 0; i < collisionObjectCnt.length; i++) {
			cnt += collisionObjectCnt[i];
		}
		return cnt;
	}
	
	public int getCount(int type){
		return collisionObjectCnt[type];
	}


	public void clear(){
		for(int i = 0; i < collisionObjectCnt.length; i++){
			collisionObjectCnt[i] = 0;
		}
		typeLessCnt = 0;
	}

	public boolean isInArray(CollisionObject object) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}