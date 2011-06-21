package com.liongrid.gameengine;

import java.io.InvalidObjectException;

import com.liongrid.gameengine.tools.FixedSizeArray;

/**
 * @author Lastis
 * This class extends ObjectHandler. This is because CollisionArea shares all of the
 * methods that ObjectHandler contains, even though all of the methods have been 
 * overridden. 
 */
public class CollisionHandler extends BaseObject implements 
		ObjectHandlerInterface<Shape.CollisionHandler>{
	
	//This is simply a two dimensional array.
	private FixedSizeArray<FixedSizeArray<Shape.CollisionHandler>> types;
	private FixedSizeArray<Shape.CollisionHandler> pendingAdditions;
	private FixedSizeArray<Shape.CollisionHandler> pendingRemovals;
	private static int[] allTypes;
	
	
	public CollisionHandler(int typeCnt, int capacity) {
		allTypes = new int[typeCnt];
		for(int i = 0; i < allTypes.length; i++) allTypes[i] = i;
		
		types = 
			new FixedSizeArray<FixedSizeArray<Shape.CollisionHandler>>(typeCnt);
		pendingAdditions = new FixedSizeArray<Shape.CollisionHandler>(capacity);
		pendingRemovals = new FixedSizeArray<Shape.CollisionHandler>(capacity);
		int length = typeCnt;
		for(int i = 0; i < length; i++){
			types.add(new FixedSizeArray<Shape.CollisionHandler>(capacity));
		}
	}
	

	/** 
	 * Will be casted to Collideable.CollisionArea. Make sure the BaseObjects used 
	 * implements one of the sub-interfaces!
	 * 
	 * @see com.liongrid.gameengine.ObjectHandlerInterface#add(com.liongrid.gameengine.BaseObject)
	 */
	public void add(Shape.CollisionHandler o){
		pendingAdditions.add((Shape.CollisionHandler) o);
	}

	/**
	 * Will be casted to Collideable.CollisionArea. Make sure the BaseObjects used 
	 * implements one of the sub-interfaces!
	 * 
	 * @see com.liongrid.gameengine.ObjectHandlerInterface#remove(com.liongrid.gameengine.BaseObject)
	 */
	public void remove(Shape.CollisionHandler o){
		pendingRemovals.add((Shape.CollisionHandler) o);
	}

	public void commitUpdates() {
		int i;
		int j;
		int length;
		int[] type;
		Shape.CollisionHandler shape;
		
		length = pendingRemovals.getCount();
		for(i = 0; i < length; i++){
			shape = pendingRemovals.get(i);
			type = shape.getType();
			for(j = 0; j < type.length; j++){
				types.get(type[j]).remove(shape, true);
			}
		}
		pendingRemovals.clear();
		
		length = pendingAdditions.getCount();
		for(i = 0; i < length; i++){
			shape = pendingAdditions.get(i);
			type = shape.getType();
			for(j = 0; j < type.length; j++){
				types.get(type[j]).add(shape);
			}
		}
		pendingAdditions.clear();
	}

	public FixedSizeArray<Shape.CollisionHandler> getObjects() {
		return null;
	}

	@Override
	public void update(float dt, BaseObject parent) {
		commitUpdates();
		clearArrays();
		
		FixedSizeArray<Shape.CollisionHandler> shapes;
		Shape.CollisionHandler shape1;
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

	private void clearArrays() {
		int length = types.getCount();
		FixedSizeArray<Shape.CollisionHandler> shapes;
		for(int i = 0; i < length ; i++){
			shapes = types.get(i);
			int count = shapes.getCount();
			for(int j = 0; j < count; j++){
				shapes.get(j).clear();
			}
		}
	}


	private void collides(Shape.CollisionHandler shape1, int typeI, 
			int shapeI, float dt) {
		
		FixedSizeArray<Shape.CollisionHandler> shapes;
		Shape.CollisionHandler shape2;
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