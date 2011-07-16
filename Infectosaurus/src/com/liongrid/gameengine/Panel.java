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

public class Panel extends GLSurfaceView implements SurfaceHolder.Callback, 
	Serializable{

	private static final long serialVersionUID = -385597431318350061L;

	
	public Panel(Context context) {
		super(context);
	    Log.d(Main.TAG,"In Panel");
	    
	}
	
	public void init() {
		// Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed
	    getHolder().addCallback(this);
	    getHolder().setType(SurfaceHolder.SURFACE_TYPE_GPU);
	    
	    GameEnginePointers gamePointers = new GameEnginePointers();
	    BaseObject.gamePointers = gamePointers;
	    gamePointers.textureLib = new TextureLibrary();
	    gamePointers.renderSystem = new RenderSystem();
	    gamePointers.panel = this;
	    gamePointers.root = new ObjectHandler();
	    gamePointers.tileSet = new TileSet();
	    gamePointers.map = new Map();
	    gamePointers.gameThread = new GameThread();
	    BaseObject.gamePointers.renderThread = new RenderingThread();
	    
	}
	
	public void addToRoot(BaseObject object){
		BaseObject.gamePointers.root.add(object);
	}
	
	public void startGame(){
		
		BaseObject.gamePointers.gameThread.start();
		
	}
	
	public void setRender(){
		setRenderer(BaseObject.gamePointers.renderThread);
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

	public void finish() {
		BaseObject.gamePointers.gameThread.stopRunning();
		
	}
}