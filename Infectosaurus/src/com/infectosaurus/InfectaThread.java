package com.infectosaurus;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class InfectaThread extends Thread {
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
        Canvas canvas = null;
        while (mRun) {
            canvas = mHolder.lockCanvas();
            if (canvas != null) {
                mPanel.doDraw(canvas);
                mHolder.unlockCanvasAndPost(canvas);
                
            }
        }
    }

	public void doTouchEvent(MotionEvent event) {
		Coordinates infector = mPanel.infector.getCoordinates();
		if(infector.distance(mPanel.getWidth()/2, mPanel.getHeight()/2) < 10){
			mPanel.color = Color.RED;
		}
	}
}
