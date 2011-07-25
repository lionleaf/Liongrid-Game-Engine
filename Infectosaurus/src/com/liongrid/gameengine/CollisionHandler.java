package com.liongrid.gameengine;

import android.util.Log;

import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.Main;

/**
 * @author Lastis
 */
public class CollisionHandler extends BaseObject 
		implements ObjectHandlerInterface<CollisionObject>{
	
	public static final int TYPE_LESS = -1;
	public static final int DEFAULT_TYPELESS_CAPACITY = 5;
	private FixedSizeArray<FixedSizeArray<CollisionObject>> types;
	private FixedSizeArray<CollisionObject> pendingAdditions;
	private FixedSizeArray<CollisionObject> pendingRemovals;
	private FixedSizeArray<CollisionObject> typeLess;
	/**
	 * This is used to faster access the elements in types
	 */
	private Object[][] rawArray;
	/**
	 * This is used to fast get the length of the sublists of types.
	 */
	private static int[] typeLengths;
	
	
	public CollisionHandler(int typeCnt, int capacity) {
		typeLengths = new int[typeCnt];
		rawArray = new Object[typeCnt][];
		
		pendingAdditions = new FixedSizeArray<CollisionObject>(capacity);
		pendingRemovals = new FixedSizeArray<CollisionObject>(capacity);
		typeLess = new FixedSizeArray<CollisionObject>(DEFAULT_TYPELESS_CAPACITY);
		types = new FixedSizeArray<FixedSizeArray<CollisionObject>>(typeCnt);
		int length = typeCnt;
		for(int i = 0; i < length; i++){
			types.add(new FixedSizeArray<CollisionObject>(capacity));
		}
	}
	

	public void add(CollisionObject o){
		pendingAdditions.add(o);
	}

	public void remove(CollisionObject o){
		pendingRemovals.add(o);
	}

	public void commitUpdates() {
		int i; int j; int length;
		int type;
		CollisionObject shape;
		
		Object[] rawArr = pendingRemovals.getArray();
		length = pendingRemovals.getCount();
		for(i = 0; i < length; i++){
			shape = (CollisionObject) rawArr[i];
			type = shape.getType();
			if(type == TYPE_LESS){
				typeLess.remove(shape, true);
				continue;
			}
			types.get(type).remove(shape, true);
		}
		pendingRemovals.clear();
		
		rawArr = pendingAdditions.getArray();
		length = pendingAdditions.getCount();
		for(i = 0; i < length; i++){
			shape = (CollisionObject) rawArr[i];
			type = shape.getType();
			if(type == TYPE_LESS){
				typeLess.add(shape);
				continue;
			}
			types.get(type).add(shape);
		}
		pendingAdditions.clear();
	}

	public FixedSizeArray<CollisionObject> getObjects() {
		return null;
	}
	

	@Override
	public void update(float dt, BaseObject parent) {
		commitUpdates();
		int typeLessCnt = typeLess.getCount();
		
		int i; int j;
		for(i = 0; i < typeLengths.length; i++){
			FixedSizeArray<CollisionObject> shapes = types.get(i);
			typeLengths[i] = shapes.getCount();
			rawArray[i]	= shapes.getArray();
		}
		
		clearArrays();
		if(typeLessCnt == 0){
			for(i = 0; i < typeLengths.length; i++){
				for(j = 0; j < typeLengths[i]; j++){
					collides(i, j, dt);
				}
			}
		}
		
		else{
			for(i = 0; i < typeLengths.length; i++){
				for(j = 0; j < typeLengths[i]; j++){
					Object[] typeLessArray = typeLess.getArray();
					for(int k = 0; k < typeLessCnt; k++){
						((CollisionObject)typeLessArray[k]).collide((CollisionObject)rawArray[i][j]);
					}
					collides(i, j, dt);
				}
			}
		}
	}
	

	private void collides(int typeI, int shapeI, float dt) {
		CollisionObject shape1 = (CollisionObject) rawArray[typeI][shapeI];
		CollisionObject shape2;
		int i; int j;
		
		for(i = typeI; i < typeLengths.length; i++){
			for(j = i == typeI ? shapeI + 1 : 0; j < typeLengths[i]; j++){
				shape2 = (CollisionObject) rawArray[i][j];
				shape1.collide(shape2);
				shape2.collide(shape1);
			}
		}
	}

	private void clearArrays() {
		for(int i = 0; i < typeLengths.length ; i++){
			for(int j = 0; j < typeLengths[i]; j++){
				((CollisionObject) rawArray[i][j]).clear();
			}
		}
	}
	
	public CollisionObject getClosest(Vector2 pos, int type) {
		CollisionObject returnO = null;
		int shape; float closestDistance = Float.MAX_VALUE;
		for(shape = 0; shape < typeLengths[type]; shape++){
			float distance = pos.distance2(((CollisionObject) rawArray[type][shape]).getPos());
			if(distance < closestDistance) {
				closestDistance = distance;
				returnO = (CollisionObject) rawArray[type][shape];
			}
		}
		return returnO;
	}
	
	/**
	 * @param pos - the position
	 * @param types - the types of the objects to return
	 * @return The closest object
	 */
	public CollisionObject getClosest(Vector2 pos, int[] types){
		CollisionObject returnO = null;
		float closestDistance = Float.MAX_VALUE; int type; int shape;
		for(int i = 0; i < types.length; i ++){
			type = types[i];
			for(shape = 0; shape < typeLengths[type]; shape++){
				float distance = pos.distance2(((CollisionObject) rawArray[type][shape]).getPos());
				if(distance < closestDistance) {
					closestDistance = distance;
					returnO = (CollisionObject) rawArray[type][shape];
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
		
		int type; int shape; int count = 0; float dis2;
		for(int i = 0; i < types.length; i ++){
			type = types[i];
			for(shape = 0; shape < typeLengths[type]; shape++){
				if(count < array.length) return array;
				dis2 = pos.distance2(((CollisionObject) rawArray[shape][type]).getPos());
				if(dis2 < withIn * withIn){
					array[count] = (CollisionObject) rawArray[type][shape];
					count ++;
				}
			}
		}
		return array;
	}
			

	@Override
	public void reset() {
		
	}


	public int getCount() {
		int cnt = 0;
		for (int i = 0; i < typeLengths.length; i++) {
			cnt += typeLengths[i];
		}
		return cnt;
	}
	
	public int getCount(int type){
		return typeLengths[type];
	}


	public void clear() throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		
	}
}