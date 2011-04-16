package com.infectosaurus;

import com.infectosaurus.map.Level;
import com.infectosaurus.map.TileSet;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
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
	    
	    GamePointers gamePointers = new GamePointers();
	    BaseObject.gamePointers = gamePointers;
	    gamePointers.renderSystem = new RenderSystem();
	    gamePointers.panel = this;
	    ObjectHandler root = new ObjectHandler();
	    gamePointers.root = root;
	    gamePointers.tileSet = new TileSet();
	    gamePointers.level = new Level();
	    GameObjectHandler gOHandler = new GameObjectHandler();
	    gamePointers.gameObjectHandler = gOHandler;
	   
	    root.add(gamePointers.level);
	    root.add(gOHandler);
	   
	   
	    
	    gThread = new GameThread();
	    rThread = new RenderingThread();
	    BaseObject.gamePointers.renderThread = rThread;
	    gThread.start();
	    setRenderer(rThread);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gThread.registerScreenTouch(event);
		return false;
	}
}