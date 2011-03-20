package com.infectosaurus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends GLSurfaceView implements SurfaceHolder.Callback{
	private static final String TAG = "MyActivity";
	private RenderingThread rThread;
	private GameThread gThread;
	
	
	public Panel(Context context) {
		super(context);
	    init();
	}
	
	private void init() {
		// Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed
	    getHolder().addCallback(this);
	    getHolder().setType(SurfaceHolder.SURFACE_TYPE_GPU);
	    rThread = new RenderingThread(this);
	    gThread = new GameThread();
	}
	 
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	    if (!rThread.isAlive()) {
	        rThread = new RenderingThread(this);
	        rThread.setRunning(true);
	        rThread.start();
	    }
	}
	 
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	    if (rThread.isAlive()) {
	        rThread.setRunning(false);
	    }
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}
	
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	gThread.doTouchEvent(event);
        return super.onTouchEvent(event);
    }
}