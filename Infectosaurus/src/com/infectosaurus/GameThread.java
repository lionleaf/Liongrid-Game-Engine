package com.infectosaurus;

import android.util.Log;
import android.view.MotionEvent;

/**
 * @author Lionleaf
 * This class is the main loop responsible for updating
 * game logic and generating a queue of objects for the 
 * rendering thread to render
 *
 */
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
    		//Make sure we don`t swap queues while renderer is rendering
    		renderThread.waitDrawingComplete();
    		
    		//Sends the previously completed renderQueue 
    		//to the renderer, and gets a new empty one
    		renderSystem.swap(renderThread);
    		
    		//TODO calculate dt
    		dt = 0;
    		
    		//Update all game logic
    		root.update(dt, null);
    	}
    }

	public void doTouchEvent(MotionEvent event) {
	}
}
