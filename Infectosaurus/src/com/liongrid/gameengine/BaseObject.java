package com.liongrid.gameengine;


/**
 * An abstract class from which everything that is updateable 
 * and reusable should extend!
 * @author Lionleaf
 * 
 */
public abstract class BaseObject {
	public static GameEnginePointers gamePointers;
	public BaseObject(){
		//Log.d("GameBoard", "In BaseObject");
	}
	
	
	/**
	 * Updates the object with a timestep dt
	 * @param dt - The timestep to be taken.
	 * @param parent - The object that called this update
	 */
	public abstract void update(float dt, BaseObject parent);
	
	/**
	 * Resets the Object for reuse
	 */
	public abstract void reset();
}
