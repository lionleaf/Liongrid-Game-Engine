package com.infectosaurus;

import java.io.Serializable;

import com.infectosaurus.map.Level;
import com.infectosaurus.map.TileSet;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class Panel extends GLSurfaceView implements SurfaceHolder.Callback, 
	Serializable{

	private static final long serialVersionUID = -385597431318350061L;
	private GestureDetector gestureDetector;
	
	public Panel(Context context) {
		super(context);
	    Log.d(Main.TAG,"In Panel");
	    
	}
	
	public void init() {
		// Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed
	    getHolder().addCallback(this);
	    getHolder().setType(SurfaceHolder.SURFACE_TYPE_GPU);
	    
	    gestureDetector = new GestureDetector(new InputSystem());
	    
	    GamePointers gamePointers = new GamePointers();
	    BaseObject.gamePointers = gamePointers;
	    
	    gamePointers.renderSystem = new RenderSystem();
	    gamePointers.panel = this;
	    gamePointers.root = new ObjectHandler();
	    gamePointers.tileSet = new TileSet();
	    gamePointers.level = new Level();
	    gamePointers.gameObjectHandler = new GameObjectHandler();
	   
	    gamePointers.root.add(gamePointers.gameObjectHandler);
	    
	    gamePointers.gameThread = new GameThread();
	    BaseObject.gamePointers.renderThread = new RenderingThread();
	    
	}
	
	public void startGame(){
		BaseObject.gamePointers.gameThread.start();
	    setRenderer(BaseObject.gamePointers.renderThread);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Gesture detection
        if (gestureDetector.onTouchEvent(event)) {
            return false;
        }
		BaseObject.gamePointers.gameThread.registerScreenTouch(event);
		return false;
	}
	
	
	@Override
	public void onPause() {
		super.onPause();
		BaseObject.gamePointers.gameThread.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		BaseObject.gamePointers.gameThread.onResume();
	}
}