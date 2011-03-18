package com.infectosaurus;

import android.graphics.Canvas;
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
}
