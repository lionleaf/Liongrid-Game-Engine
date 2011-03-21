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
	    Log.d(TAG,"In Panel");
	    init();
	}
	
	private void init() {
		// Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed
	    getHolder().addCallback(this);
	    getHolder().setType(SurfaceHolder.SURFACE_TYPE_GPU);
	    rThread = new RenderingThread(this);
	    gThread = new GameThread();
	    setRenderer(rThread);
	}
}