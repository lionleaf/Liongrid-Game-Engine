package com.liongrid.gameengine;

import java.io.Serializable;

import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.Main;
import com.liongrid.infectosaurus.map.Map;
import com.liongrid.infectosaurus.map.TileSet;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.SurfaceHolder;

public class LPanel extends GLSurfaceView implements SurfaceHolder.Callback, 
	Serializable{

	private static final long serialVersionUID = -385597431318350061L;

	
	public LPanel(Context context) {
		super(context);
	    Log.d(Main.TAG,"In LPanel");
	    
	}
	
	public void init() {
		// Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed
	    getHolder().addCallback(this);
	    getHolder().setType(SurfaceHolder.SURFACE_TYPE_GPU);
	    
	    LGameEnginePointers gamePointers = new LGameEnginePointers();
	    LBaseObject.gamePointers = gamePointers;
	    gamePointers.textureLib = new LTextureLibrary();
	    gamePointers.renderSystem = new LRenderSystem();
	    gamePointers.panel = this;
	    gamePointers.root = new LObjectHandler();
	    gamePointers.tileSet = new TileSet();
	    gamePointers.map = new Map();
	    gamePointers.gameThread = new LGameThread();
	    LBaseObject.gamePointers.renderThread = new LRenderingThread();
	    
	}
	
	public void addToRoot(LBaseObject object){
		LBaseObject.gamePointers.root.add(object);
	}
	
	public void startGame(){
		
		LBaseObject.gamePointers.gameThread.start();
		
	}
	
	public void setRender(){
		setRenderer(LBaseObject.gamePointers.renderThread);
	}
	

	
	@Override
	public void onPause() {
		super.onPause();
		LBaseObject.gamePointers.gameThread.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		LBaseObject.gamePointers.gameThread.onResume();
	}

	public void finish() {
		LBaseObject.gamePointers.gameThread.stopRunning();
		
	}
}