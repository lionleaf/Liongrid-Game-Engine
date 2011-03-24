package com.infectosaurus;

import android.util.Log;
import android.view.MotionEvent;

public class GameThread extends Thread {
    
	private GameObjectHandler objectHandler;
 
	private boolean running = true;
	private final static String TAG = "GameThread";
    public GameThread(GameObjectHandler gameHandler) {
    	objectHandler = gameHandler;
    	setName("GameThread");
    	Log.d(TAG, "Starting GameThread");
    }
 
    @Override
    public void run() {
    	Log.d("GameBoard", "In gameThread  :D");
    	while(running){
    		updatePhysics();
    	}
    }

	public void doTouchEvent(MotionEvent event) {
	}
	
	private void updatePhysics(){
		objectHandler.update4Game();
	}
}
