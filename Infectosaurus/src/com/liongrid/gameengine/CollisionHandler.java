package com.liongrid.gameengine;

import java.io.InvalidObjectException;

import com.liongrid.gameengine.tools.FixedSizeArray;

/**
 * @author Lastis
 * This class extends ObjectHandler. This is because CollisionArea shares all of the
 * methods that ObjectHandler contains, even though all of the methods have been 
 * overridden. 
 */
public class CollisionHandler<T extends Shape.CollisionHandler<T>> extends BaseObject 
		implements ObjectHandlerInterface<T>{
	
	//This is simply a two dimensional array.
	private FixedSizeArray<FixedSizeArray<T>> types;
	private FixedSizeArray<T> pendingAdditions;
	private FixedSizeArray<T> pendingRemovals;
	private static int[] allTypes;
	
	
	public CollisionHandler(int typeCnt, int capacity) {
		allTypes = new int[typeCnt];
		for(int i = 0; i < allTypes.length; i++) allTypes[i] = i;
		
		types = 
			new FixedSizeArray<FixedSizeArray<T>>(typeCnt);
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
		int[] type;
		T shape;
		
		
		
		Object[] rawArr = pendingRemovals.getArray();
		length = pendingRemovals.getCount();
		for(i = 0; i < length; i++){
			shape = (T) rawArr[i];
			type = shape.getType();
			for(j = 0; j < type.length; j++){
				types.get(type[j]).remove(shape, true);
			}
		}
		pendingRemovals.clear();
		
		rawArr = pendingAdditions.getArray();
		length = pendingAdditions.getCount();
		for(i = 0; i < length; i++){
			shape = (T) rawArr[i];
			type = shape.getType();
			for(j = 0; j < type.length; j++){
				types.get(type[j]).add(shape);
			}
		}
		pendingAdditions.clear();
	}

	public FixedSizeArray<T> getObjects() {
		return null;
	}
	
	private void clearArrays() {
		int length = types.getCount();
		FixedSizeArray<T> shapes;
		for(int i = 0; i < length ; i++){
			shapes = types.get(i);
			int count = shapes.getCount();
			for(int j = 0; j < count; j++){
				shapes.get(j).clear();
			}
		}
	}

	@Override
	public void update(float dt, BaseObject parent) {
		commitUpdates();
		clearArrays();
		
		FixedSizeArray<T> shapes;
		T shape1;
		int length; int count; 
		length = types.getCount();
		for(int i = 0; i < length; i++){
			shapes = types.get(i);
			count = shapes.getCount();
			for(int j = 0; j < count; j++){
				shape1 = shapes.get(j);
				collides(shape1, i, j, dt);
			}
		}
	}



	private void collides(T shape1, int typeI, int shapeI, float dt) {
		
		FixedSizeArray<T> shapes;
		T shape2;
		int[] pCol1; int[] pCol2; // Possible Collisions
		int i; int j; int type1; int type2;
		int length; int count;
		boolean test1 = false; boolean test2 = false;
		
		type1 = typeI;
		pCol1 = shape1.getPossibleCollisions();
		if(pCol1 == null) pCol1 = allTypes;
		
		length = types.getCount();
		for(i = typeI; i < length; i++){
			shapes = types.get(i);
			count = shapes.getCount();
			for(j = shapeI + 1; j < count; j++){
				shape2 = shapes.get(j);
				type2 = j;
				pCol2 = shape2.getPossibleCollisions();
				if(pCol2 == null) pCol2 = allTypes;
				
				
				// This loop sets test2 = true if a number in pCol1 matches type2.
				for(int u: pCol1){ if(u == type2){ test1 = true; break;}}
				// This loop sets test1 = true if a number in pCol1 matches type2.
				for(int u: pCol2){ if(u == type1){ test2 = true; break;}}
				
				// If both test are negative, these two shapes can't collide.
				if(!test1 && !test2) continue;
				
				//TODO This code is never reached!
				shape1.expandHitbox(dt);
				shape2.expandHitbox(dt);
				if(Collision.collides(shape1, shape2)){
					if(test1) shape1.collide(shape2);
					if(test2) shape2.collide(shape1);
				}
				shape1.resetHitbox();
				shape2.resetHitbox();
			}
		}
	}


	@Override
	public void reset() {
		
	}
}