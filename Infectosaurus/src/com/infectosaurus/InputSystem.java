package com.infectosaurus;

import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;
import com.infectosaurus.map.Level;

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
		// Adjust pos.x settings for landscape mode
		Camera.pos.x += (int) distanceX / Camera.scale;
		if(Camera.pos.x + Camera.screenHeight > Level.mapSizePx.x * Camera.scale) 
			Camera.pos.x = (int) (Level.mapSizePx.x*Camera.scale - Camera.screenHeight);
		if(Camera.pos.x < 0) Camera.pos.x = 0;
		
		Camera.pos.y -= (int) distanceY / Camera.scale;
		if(Camera.pos.y + Camera.screenWidth > Level.mapSizePx.y * Camera.scale) 
			Camera.pos.y = (int) (Level.mapSizePx.y * Camera.scale - Camera.screenWidth);
		if(Camera.pos.y < 0) Camera.pos.y = 0;
		return true;
	}
}
