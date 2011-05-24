package com.infectosaurus;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

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
		Camera.pos.x = Camera.screenWidth - (int) e1.getX();
		Camera.pos.y = (int) e1.getY();
		return true;
	}
}
