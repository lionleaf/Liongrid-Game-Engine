package com.infectosaurus;

import android.util.Log;
import android.view.MotionEvent;

public class GameThread extends Thread {
    
	private GameObjectHandler objectHandler;
 
    public GameThread(GameObjectHandler gameHandler) {
    	objectHandler = gameHandler;
    }
 
    @Override
    public void run() {
    	Log.d("GameBoard", "In gameThread :D");
		updatePhysics();
    }

	public void doTouchEvent(MotionEvent event) {
	}
	
	private void updatePhysics(){
		objectHandler.update4Game();
	}
}
