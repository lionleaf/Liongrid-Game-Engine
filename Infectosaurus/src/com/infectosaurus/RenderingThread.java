package com.infectosaurus;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class RenderingThread extends Thread {
    
    private Panel mPanel;
    private SurfaceHolder mHolder;
    private boolean mRun = false;
 
    public RenderingThread(Panel panel) {
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
}
