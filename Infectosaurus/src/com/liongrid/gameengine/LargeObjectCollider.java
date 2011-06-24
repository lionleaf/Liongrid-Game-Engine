package com.liongrid.gameengine;

import java.io.InvalidObjectException;

import android.R.raw;
import android.util.Log;

import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.Main;

/**
 * @author Lastis
 * This class extends ObjectHandler. This is because CollisionArea shares all of the
 * methods that ObjectHandler contains, even though all of the methods have been 
 * overridden. 
 */
public class LargeObjectCollider<T extends Shape.Collideable<T>> extends BaseObject 
		implements ObjectHandlerInterface<T>{
	
	private FixedSizeArray<FixedSizeArray<T>> types;
	private FixedSizeArray<T> pendingAdditions;
	private FixedSizeArray<T> pendingRemovals;
	private FixedSizeArray<T> typeLess;
	/**
	 * This is used to faster access the elements in types
	 */
	private Object[][] rawArray;
	private int arrayCnt;
	/**
	 * This is used to fast get the length of the sublists of types.
	 */
	private static int[] arrayLengths;
	
	
	public LargeObjectCollider(int typeCnt, int capacity) {
		arrayCnt = 0;
		arrayLengths = new int[typeCnt];
		rawArray = new Object[typeCnt][];
		
		pendingAdditions = new FixedSizeArray<T>(capacity);
		pendingRemovals = new FixedSizeArray<T>(capacity);
		typeLess = new FixedSizeArray<T>(capacity);
		types = new FixedSizeArray<FixedSizeArray<T>>(typeCnt);
		int length = typeCnt;
		for(int i = 0; i < length; i++){
			types.add(new FixedSizeArray<T>(capacity));
		}
	}
	

	public void add(T o){
		pendingAdditions.add(o);
	}

	public void remove(T o){
		pendingRemovals.add(o);
	}

	public void commitUpdates() {
		int i;
		int j;
		int length;
		int type;
		T shape;
		
		Object[] rawArr = pendingRemovals.getArray();
		length = pendingRemovals.getCount();
		for(i = 0; i < length; i++){
			shape = (T) rawArr[i];
			type = shape.getType();
			types.get(type).remove(shape, true);
		}
		pendingRemovals.clear();
		
		rawArr = pendingAdditions.getArray();
		length = pendingAdditions.getCount();
		for(i = 0; i < length; i++){
			shape = (T) rawArr[i];
			type = shape.getType();
			types.get(type).add(shape);
		}
		pendingAdditions.clear();
	}

	public FixedSizeArray<T> getObjects() {
		return null;
	}
	

	@Override
	public void update(float dt, BaseObject parent) {
		commitUpdates();
		int typeLessCnt = typeLess.getCount();
		
		arrayCnt = 0;
		int i; int j;
		for(i = 0; i < arrayLengths.length; i++){
			FixedSizeArray<T> shapes = types.get(i);
			arrayLengths[i] = shapes.getCount();
			arrayCnt += arrayLengths[i];
			rawArray[i]	= shapes.getArray();
		}
		
		clearArrays();
		
		for(i = 0; i < arrayLengths.length; i++){
			for(j = 0; j < arrayLengths[i]; j++){
				if(typeLessCnt != 0){
					Object[] typeLessArray = typeLess.getArray();
					for(int k = 0; k < typeLessCnt; k++){
						((T) typeLessArray[k]).collide((T) rawArray[i][j]);
					}
				}
				collides(i, j, dt);
			}
		}
	}
	

	private void collides(int typeI, int shapeI, float dt) {
		T shape1 = (T) rawArray[typeI][shapeI];
		T shape2;
		int i; int j;
		
		for(i = typeI; i < arrayLengths.length; i++){
			for(j = shapeI + 1; j < arrayLengths[i]; j++){
				shape2 = (T) rawArray[i][j];
				shape1.collide(shape2);
				shape2.collide(shape1);
			}
		}
	}

	private void clearArrays() {
		for(int i = 0; i < arrayLengths.length ; i++){
			for(int j = 0; j < arrayLengths[i]; j++){
				((T) rawArray[i][j]).clear();
			}
		}
	}
	
	/**
	 * @param pos - the position
	 * @param types - the types of the objects to return
	 * @return The closest object
	 */
	public T getClosest(Vector2 pos, int[] types){
		if(arrayCnt == 0) return null;
		T returnO = null;
		float closestDistance = 0; int type; int shape;
		for(int i = 0; i < types.length; i ++){
			type = types[i];
			for(shape = 0; shape < arrayLengths[type]; shape++){
				if(returnO == null) {
					returnO = (T) rawArray[type][shape];
					closestDistance = pos.distance2(returnO.getPos());
					continue;
				}
				float distance = pos.distance2(((T) rawArray[type][shape]).getPos());
				if(distance < closestDistance) {
					closestDistance = distance;
					returnO = (T) rawArray[type][shape];
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
	public T[] getClose(Vector2 pos, float withIn, int[] types, T[] array){
		if(arrayCnt == 0) return null;
		int type; int shape; int count = 0; float dis2;
		for(int i = 0; i < types.length; i ++){
			type = types[i];
			for(shape = 0; shape < arrayLengths[type]; shape++){
				if(count < array.length) return array;
				dis2 = pos.distance2(((T) rawArray[shape][type]).getPos());
				if(dis2 < withIn * withIn){
					array[count] = (T) rawArray[type][shape];
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
		return arrayCnt;
	}
}