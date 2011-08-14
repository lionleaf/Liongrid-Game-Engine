package com.liongrid.gameengine;


import com.liongrid.gameengine.tools.LVector2;

/**
 * @author Lastis
 *	A LCollisionObject is a object is used in the LCollisionHandler. A LCollisionObject needs 
 *	to implement a subinterface of LShape to work. A LCollisionObject is basically a shape 
 *	with an array of all the other shapes it collides with. 
 */
public abstract class LCollisionObject implements LShape{
	
	public static final int DEFAULT_MAX_COLLISIONS = 10;
	
	// Made public for speed.
	public int collisionCnt;
	public LCollisionObject[] collisions;
	public LGameObject owner;
	public int type;
	
	/**
	 * A LCollisionObject is basically a shape with an array of all the other shapes 
	 * it collides with. It is used in LCollisionHandler. The LCollisionObject has a max
	 * number of objects it will store in its array.
	 * 
	 * @param type - 
	 * @param pos - The position of the shape.
	 * @param owner - A pointer to the owner of the shape.
	 * @param maxCollisions - Number of max collisions the LCollisionObject will store
	 * in its array.
	 */
	public LCollisionObject(int type, LGameObject owner, int maxCollisions) {
		this.type = type;
		this.owner = owner;
		collisions = new LCollisionObject[maxCollisions];
	}
	
	/**
	 * A LCollisionObject is basically a shape with an array of all the other shapes 
	 * it collides with. It is used in LCollisionHandler. The LCollisionObject has a max
	 * number of objects it will store in its array. This uses the default value for
	 * the size of the collisions array.
	 * 
	 * @param type - LCollisionObject are divided into different arrays in LCollisionHandler
	 * according to their types.
	 * @param pos - The position of the shape.
	 * @param owner - A pointer to the owner of the shape.
	 * in its array.
	 */
	public LCollisionObject(int type, LGameObject<?> owner) {
		this.type = type;
		this.owner = owner;
		collisions = new LCollisionObject[DEFAULT_MAX_COLLISIONS];
	}

	/**
	 * This is called by the LCollisionHandler and is called once with every other 
	 * LCollisionObject in the arrays of LCollisionHandler.
	 * @param object - The shape that the CollisionShape collides with.  
	 */
	public void collide(LCollisionObject object){
		if(collisionCnt >= collisions.length || inArray(object)) return;
		if(LCollision.collides(this, object)){
			collisions[collisionCnt] = object;
			collisionCnt++;
		}
	}
	
	public boolean inArray(LCollisionObject object) {
		for(int i = 0; i < collisionCnt; i++){
			if(collisions[i] == object) return true;
		}
		return false;
	}

	/**
	 * Erases old history and makes the shape ready for new collisions.
	 * This is usually called before a set of .collides(shape) calls.
	 */
	public void clear(){
		collisionCnt = 0;
		for(int i = 0; i < collisions.length; i++){
			collisions[i] = null;
		}
	}
	
	/**
	 * @return the index of the type the object represents.
	 */
	public int getType(){
		return type;
	}
	
	public LVector2 getPos() {
		return owner.pos;
	}
}
