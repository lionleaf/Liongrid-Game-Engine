package com.infectosaurus;

import android.util.Log;


/**
 * @author Lionleaf
 * An abstract class from which everything that is updateable 
 * and reusable should extend!
 */
public abstract class BaseObject {
	public static final String TAG = "Infectosaurus";
	public static GamePointers gamePointers;
	public BaseObject(){
		//Log.d("GameBoard", "In BaseObject");
	}
	
	
	/**
	 * Updates the object with a timestep dt
	 * @param dt - The timestep to be taken.
	 * @param parent - The object that called this update
	 */
	public void update(float dt, BaseObject parent){
		Log.d("Infectosaurus", "Someone has not overriden update!");
	}
	
	/**
	 * Resets the Object for reuse
	 */
	public abstract void reset();
}
