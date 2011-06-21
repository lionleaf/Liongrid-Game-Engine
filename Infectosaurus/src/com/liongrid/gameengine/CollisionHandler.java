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
		
		int length = types.getCount();
		for(int i = 0; i < length; i++){
			FixedSizeArray<Shape.CollisionHandler> shapes = types.get(i);
			int count = shapes.getCount();
			for(int j = 0; j < count; j++){
				Shape.CollisionHandler shape1 = shapes.get(j);
				collides(shape1, i, j, dt);
			}
		}
	}

	private void collides(Shape.CollisionHandler shape1, int typeI, 
			int shapeI, float dt) {
		int i; int j;
		FixedSizeArray<Shape.CollisionHandler> shapes;
//		int[] possibleCollisions = shape1.getPossibleCollisions();
//		if(possibleCollisions == null) possibleCollisions = allTypes;
//		for(i = 0; i <= typeI; i++){
//			
//		}
		
		int length = types.getCount();
		for(i = typeI; i < length; i++){
			shapes = types.get(i);
			int count = shapes.getCount();
			for(j = shapeI + 1; j < count; j++){
				Shape.CollisionHandler shape2 = shapes.get(j);
				if(Collision.collides(shape1, shape2)){
					shape1.expandHitbox(dt);
					shape2.expandHitbox(dt);
					shape1.collides(shape2);
					shape2.collides(shape1);
					shape1.resetHitbox();
					shape2.resetHitbox();
				}
			}
		}
	}


	@Override
	public void reset() {
		
	}
}