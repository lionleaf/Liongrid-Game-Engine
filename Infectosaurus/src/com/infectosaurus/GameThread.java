package com.infectosaurus;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    
    private boolean mRun = false;
 
    public GameThread() {
    }
 
    public void setRunning(boolean run) {
        mRun = run;
    }
 
    @Override
    public void run() {
    	
    }

	public void doTouchEvent(MotionEvent event) {
	}
	
	private void updatePhysics(){
	}
}
