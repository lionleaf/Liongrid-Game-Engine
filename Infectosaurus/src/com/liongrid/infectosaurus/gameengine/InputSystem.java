package com.liongrid.infectosaurus.gameengine;

import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

import com.liongrid.infectosaurus.Main;
import com.liongrid.infectosaurus.map.Level;

public class InputSystem extends SimpleOnGestureListener{

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		BaseObject.gamePointers.gameThread.registerScreenTouch(event);
		return true;
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		// This must be here for the gesture listener to work!!!!!
		return false;
	}
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, 
			float distanceX, float distanceY) {
		
		BaseObject.gamePointers.renderThread.waitDrawingComplete();
		
		// Adjust pos.x settings for landscape mode
		Camera.pos.x += (int) distanceX / Camera.scale;
		Log.d(Main.TAG,"mapSizePx.x = " + Level.mapSizePx.x);
		Log.d(Main.TAG,"screenWidth = " + Camera.screenWidth);
		Log.d(Main.TAG,"screenWidth/scale = " + Camera.screenWidth/Camera.scale);
		Log.d(Main.TAG,"scale = " + Camera.scale);
		if(Camera.pos.x  < 0) Camera.pos.x = 0;
		else if(Camera.pos.x + Camera.screenWidth/Camera.scale > Level.mapSizePx.x) 
			Camera.pos.x = Math.max(0, 
					Level.mapSizePx.x - (int)( Camera.screenWidth/Camera.scale));
		
		Camera.pos.y -= (int) distanceY / Camera.scale;
		if(Camera.pos.y  < 0) Camera.pos.y = 0;
		else if(Camera.pos.y  + Camera.screenHeight/Camera.scale > Level.mapSizePx.y) 
			Camera.pos.y = Math.max(0, 
					(Level.mapSizePx.y) - (int) (Camera.screenHeight/Camera.scale));
		return true;
	}
}
