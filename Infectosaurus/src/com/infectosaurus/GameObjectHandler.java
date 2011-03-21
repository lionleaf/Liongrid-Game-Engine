package com.infectosaurus;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

/**
 * @author Lastis
 * This class needs to hold all the GameObjects and sort the 
 * components in a way that they can be done in the right order 
 */
public class GameObjectHandler {
	
	private static final String TAG = "GameBoard";
	private Infectosaurus infector;

	GameObjectHandler(Panel panel){
		Log.d(TAG,"In GameObjectHandler");
		infector = new Infectosaurus(panel);
	}

	public void update4Renderer(GL10 gl) {
		infector.useComp4Renderer(gl);
	}
}
