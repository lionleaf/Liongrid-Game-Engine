package com.liongrid.gameengine;

import java.io.Serializable;

import com.liongrid.infectosaurus.IMainMenuActivity;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.SurfaceHolder;

public class LSurfaceViewPanel extends GLSurfaceView implements SurfaceHolder.Callback, 
	Serializable{

	private static final long serialVersionUID = -385597431318350061L;

	
	public LSurfaceViewPanel(Context context) {
		super(context);
	    Log.d(IMainMenuActivity.TAG,"In LSurfaceViewPanel");
	    
	}
	
	public void init() {
		// Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed
	    getHolder().addCallback(this);
	    getHolder().setType(SurfaceHolder.SURFACE_TYPE_GPU);
	    
	    
	    
	}
	
	public void addToRoot(LBaseObject object){
		LGamePointers.root.add(object);
	}
	
	public synchronized void startGame(){
		
		LGamePointers.gameThread.start();
		
	}
	
	public synchronized void setRender(){
		setRenderer(LGamePointers.renderThread);
	}
	

	
	@Override
	public void onPause() {
		super.onPause();
		LGamePointers.gameThread.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		LGamePointers.gameThread.onResume();
	}

	public void finish() {
		LGamePointers.gameThread.stopRunning();
		
	}
}