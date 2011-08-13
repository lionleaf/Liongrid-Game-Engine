package com.liongrid.gameengine;

import android.view.MotionEvent;

public interface LInputDispatchInterface {
	boolean dispatchSingleTapUp(MotionEvent event);
	boolean dispatchTouchDown(MotionEvent e);
	boolean dispatchScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
	boolean dispatchLongPress(MotionEvent e);
	boolean dispatchShowPress(MotionEvent e);
}
