package com.infectosaurus;


import android.util.Log;
import android.view.GestureDetector;
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
		return true;
	}
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// Adjust pos.x settings for landscape mode
		Camera.pos.x += (int) distanceX;
		if(Camera.pos.x < 0) Camera.pos.x = 0;
		if(Camera.pos.x + Camera.screenWidth > Level.mapSizePx.x * Level.TILE_SIZE) 
			Camera.pos.x = Level.mapSizePx.x * Level.TILE_SIZE;
		
		Camera.pos.y += (int) -distanceY;
		if(Camera.pos.y < 0) Camera.pos.y = 0;
		if(Camera.pos.y + Camera.screenHeight > Level.mapSizePx.y * Level.TILE_SIZE) 
			Camera.pos.y = Level.mapSizePx.y * Level.TILE_SIZE;
		return true;
	}
}
