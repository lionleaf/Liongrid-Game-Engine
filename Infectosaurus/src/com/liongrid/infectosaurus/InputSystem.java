package com.liongrid.infectosaurus;

import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Camera;
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
		
		//Make sure not to put any value into the camera that 
		//we are not sure are valid
		float tempX = Camera.pos.x + distanceX / Camera.scale;
		
//		Log.d(Main.TAG,"mapSizePx.x = " + Level.mapSizePx.x);
//		Log.d(Main.TAG,"screenWidth = " + Camera.screenWidth);
//		Log.d(Main.TAG,"screenWidth/scale = " + Camera.screenWidth/Camera.scale);
//		Log.d(Main.TAG,"scale = " + Camera.scale);
		
		
		if(tempX  < 0) Camera.pos.x = 0;
		else if(tempX + Camera.screenWidth/Camera.scale > Level.mapSizePx.x) 
			Camera.pos.x = Math.max(0, 
					Level.mapSizePx.x - (int)( Camera.screenWidth/Camera.scale));
		else{
			Camera.pos.x = (int) tempX;
		}
		
		float tempY = Camera.pos.y - distanceY / Camera.scale;
		if(tempY  < 0){
			Camera.pos.y = 0;
		}else if(tempY  + Camera.screenHeight/Camera.scale > Level.mapSizePx.y){ 
			Camera.pos.y = Math.max(0, 
					(Level.mapSizePx.y) - (int) (Camera.screenHeight/Camera.scale));
		}else{
			Camera.pos.y = (int) tempY;
		}
		
		
		return true;
	}
}
