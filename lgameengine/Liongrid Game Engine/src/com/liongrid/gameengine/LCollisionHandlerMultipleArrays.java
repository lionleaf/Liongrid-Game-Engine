package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.gameengine.tools.LVector2;

public class LCollisionHandlerMultipleArrays extends LBaseObject 
		implements LObjectHandlerInterface<LCollisionObject>{
	
	public static final int TYPE_LESS = -1;
	public static final int DEFAULT_TYPELESS_CAPACITY = 5;
	
	private LCollisionObject[][] collisionObjects;
	private LCollisionObject[] typeLess;
	private int[] collisionObjectCnt;
	private int typeLessCnt;
	private LFixedSizeArray<LCollisionObject> pendingAdditions;
	
	
	public LCollisionHandlerMultipleArrays(int typeCnt, int capacity) {
		pendingAdditions = new LFixedSizeArray<LCollisionObject>(capacity);
		typeLess = new LCollisionObject[DEFAULT_TYPELESS_CAPACITY];
		typeLessCnt = 0;
		
		collisionObjects = new LCollisionObject[typeCnt][];
		for(int i = 0; i < collisionObjects.length; i++){
			collisionObjects[i] = new LCollisionObject[DEFAULT_CAPACITY];
		}
		
		collisionObjectCnt = new int[typeCnt];
		for(int i = 0; i < collisionObjectCnt.length; i++){
			collisionObjectCnt[i] = 0;
		}
	}

	public void add(LCollisionObject o){
		pendingAdditions.add(o);
	}

	public void remove(LCollisionObject o) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}

	public void commitUpdates() {
		int type;
		LCollisionObject collisionObject;
		Object[] rawArr = pendingAdditions.getArray();
		int length = pendingAdditions.getCount();
		for(int i = 0; i < length; i++){
			collisionObject = (LCollisionObject) rawArr[i];
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

	public LFixedSizeArray<LCollisionObject> getObjects() 
			throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	

	@Override
	public void update(float dt, LBaseObject parent) {
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
		LCollisionObject shape1 = collisionObjects[typeIndex][shapeIndex];
		LCollisionObject shape2;
		
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
	public LCollisionObject getClosest(LVector2 pos, int type) {
		LCollisionObject returnO = null;
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
	public LCollisionObject getClosest(LVector2 pos, int[] types){
		LCollisionObject returnO = null;
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
	public LCollisionObject[] getClose(LVector2 pos, float withIn, 
			int[] types, LCollisionObject[] array){
		
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
	public LCollisionObject[] getClose(LVector2 pos, float withIn, 
			int type, LCollisionObject[] array){
		
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

	public boolean inArray(LCollisionObject object) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}