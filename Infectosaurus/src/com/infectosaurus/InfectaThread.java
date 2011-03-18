package com.infectosaurus;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class InfectaThread extends Thread {
	/** Used to figure out elapsed time between frames */
    private long mLastTime;
    
    private Panel mPanel;
    private SurfaceHolder mHolder;
    private boolean mRun = false;
 
    public InfectaThread(Panel panel) {
        mPanel = panel;
        mHolder = mPanel.getHolder();
    }
 
    public void setRunning(boolean run) {
        mRun = run;
    }
 
    @Override
    public void run() {
        while (mRun) {
        	Canvas canvas = null;
        	try{
	            canvas = mHolder.lockCanvas();
	            if (canvas != null) {
	            	mLastTime = System.currentTimeMillis() + 100;
	                mPanel.doDraw(canvas);
	            }
        	}finally {
                // do this in a finally so that if an exception is thrown
                // during the above, we don't leave the Surface in an
                // inconsistent state
                if (canvas != null) {
                    mHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
        
    }

	public void doTouchEvent(MotionEvent event) {
		Coordinates infector = mPanel.infector.getCoordinates();
		if(infector.distance(mPanel.getWidth()/2, mPanel.getHeight()/2) < 10){
			mPanel.color = Color.RED;
		}
	}
	
	private void updatePhysics(){
		 long now = System.currentTimeMillis();

         // Do nothing if mLastTime is in the future.
         // This allows the game-start to delay the start of the physics
         // by 100ms or whatever.
         if (mLastTime > now) return;

         double elapsed = (now - mLastTime) / 1000.0;
         
         mLastTime = now;
	}
}
