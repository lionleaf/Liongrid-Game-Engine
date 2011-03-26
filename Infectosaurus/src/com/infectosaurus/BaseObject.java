package com.infectosaurus;

import android.util.Log;

public abstract class BaseObject {
	public static GamePointers gamePointers;
	public BaseObject(){
		//Log.d("GameBoard", "In BaseObject");
	}
	
	public void update(float dt, BaseObject parent){
		Log.d("Infectosaurus", "Someone has not overriden update!");
	}
	
	/**
	 * Resets the Object for reuse
	 */
	public abstract void reset();
}
