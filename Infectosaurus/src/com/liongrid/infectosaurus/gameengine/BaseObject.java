package com.liongrid.infectosaurus.gameengine;

import com.liongrid.infectosaurus.Main;

import android.util.Log;


/**
 * An abstract class from which everything that is updateable 
 * and reusable should extend!
 * @author Lionleaf
 * 
 */
public abstract class BaseObject {
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
		Log.d(Main.TAG, this.getClass().getSimpleName()+ " has not overriden update!");
	}
	
	/**
	 * Resets the Object for reuse
	 */
	public abstract void reset();
}
