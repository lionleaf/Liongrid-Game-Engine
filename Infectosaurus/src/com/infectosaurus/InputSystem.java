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
		// TODO Auto-generated method stub
		return true;
	}
	
//	@Override
//	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//		Log.d(Main.TAG, "Scroll event");
//		Camera.cameraPosition[0] = (int) e2.getX();
//		Camera.cameraPosition[1] = (int) e2.getY();
//		return true;
//	}
}
