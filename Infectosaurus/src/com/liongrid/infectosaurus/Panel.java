package com.liongrid.infectosaurus;

import java.io.Serializable;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.GamePointers;
import com.liongrid.gameengine.GameThread;
import com.liongrid.gameengine.RenderSystem;
import com.liongrid.gameengine.RenderingThread;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.infectosaurus.map.Level;
import com.liongrid.infectosaurus.map.TileSet;
import com.liongrid.infectosaurus.tools.InfectoGameObjectHandler;
import com.liongrid.infectosaurus.tools.ObjectHandler;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
	    
	    GamePointers gamePointers = new GamePointers();
	    BaseObject.gamePointers = gamePointers;
	    gamePointers.longTermTexLib = new TextureLibrary();
	    gamePointers.shortTermTexLib = new TextureLibrary();
	    gamePointers.renderSystem = new RenderSystem();
	    gamePointers.panel = this;
	    gamePointers.root = new ObjectHandler();
	    gamePointers.tileSet = new TileSet();
	    gamePointers.level = new Level();
	    gamePointers.gameObjectHandler = new InfectoGameObjectHandler();
	   
	    gamePointers.root.add(gamePointers.gameObjectHandler);
	    
	    loadTextures();
	    
	    gamePointers.gameThread = new GameThread();
	    BaseObject.gamePointers.renderThread = new RenderingThread();
	    
	}
	
	public void startGame(){
		BaseObject.gamePointers.gameThread.start();
	    
	}
	
	public void loadTextures(){
		TextureLibrary tLib = BaseObject.gamePointers.shortTermTexLib;
		tLib.allocateTexture(R.drawable.lumberinghulklo);
		tLib.allocateTexture(R.drawable.mann1);
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
}