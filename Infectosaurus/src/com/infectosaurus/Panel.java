package com.infectosaurus;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.SurfaceHolder;

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
	    GameObjectHandler gameHandler = new GameObjectHandler(this);
	    gThread = new GameThread(gameHandler);
	    gThread.start();
	    rThread = new RenderingThread(gameHandler);
	    setRenderer(rThread);
	}
}