package com.infectosaurus;

import android.util.Log;
import android.view.MotionEvent;

public class GameThread extends Thread {
    
	private ObjectHandler root;
 
	private float dt;
	private boolean running = true;
	private final static String TAG = "GameThread";
	RenderSystem renderSystem;
	RenderingThread renderThread;
    public GameThread() {
    	root = BaseObject.gamePointers.root;
    	setName("GameThread");
    }
 
    @Override
    public void run() {
    	renderSystem = BaseObject.gamePointers.renderSystem;
    	renderThread = BaseObject.gamePointers.renderThread;
    	Log.d(TAG, "Starting GameThread");
    	while(running){
    		renderSystem.swap(renderThread);
    		dt = 0;
    		root.update(dt, null);
    	}
    }

	public void doTouchEvent(MotionEvent event) {
	}
}
