package com.liongrid.gameengine;

import java.io.InvalidObjectException;

import android.util.Log;

import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.infectosaurus.Main;

/**
 * @author Lastis
 * This class extends ObjectHandler. This is because CollisionArea shares all of the
 * methods that ObjectHandler contains, even though all of the methods have been 
 * overridden. 
 */
public class CollisionHandler<T extends Shape.CollisionHandler<T>> extends BaseObject 
		implements ObjectHandlerInterface<T>{
	
	private FixedSizeArray<FixedSizeArray<T>> types;
	private FixedSizeArray<T> pendingAdditions;
	private FixedSizeArray<T> pendingRemovals;
	/**
	 * This is used to faster access the elements in types
	 */
	private Object[][] rawArray;
	/**
	 * This is used to fast get the length of the sublists of types.
	 */
	private static int[] arrayLengths;
	
	
	public CollisionHandler(int typeCnt, int capacity) {
		arrayLengths = new int[typeCnt];
		rawArray = new Object[typeCnt][];
		
		types = new FixedSizeArray<FixedSizeArray<T>>(typeCnt);
		pendingAdditions = new FixedSizeArray<T>(capacity);
		pendingRemovals = new FixedSizeArray<T>(capacity);
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
		int i; int j;
		for(i = 0; i < arrayLengths.length; i++){
			FixedSizeArray<T> shapes = types.get(i);
			arrayLengths[i] = shapes.getCount();
			rawArray[i]	= shapes.getArray();
		}
		
		commitUpdates();
		clearArrays();
		
		for(i = 0; i < arrayLengths.length; i++){
			for(j = 0; j < arrayLengths[i]; j++){
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
				
				shape1.expandHitbox(dt);
				shape2.expandHitbox(dt);
				if(Collision.collides(shape1, shape2)){
					shape1.collide(shape2);
					shape2.collide(shape1);
				}
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

	@Override
	public void reset() {
		
	}
}