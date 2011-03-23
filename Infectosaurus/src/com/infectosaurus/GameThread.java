package com.infectosaurus;

import android.view.MotionEvent;

public class GameThread extends Thread {
    
    private boolean mRun = false;
 
    public GameThread(GameObjectHandler gameHandler) {
    }
 
    public void setRunning(boolean run) {
        mRun = run;
    }
 
    @Override
    public void run() {
    	if(!mRun) return;
    	
    }

	public void doTouchEvent(MotionEvent event) {
	}
	
	private void updatePhysics(){
	}
}
