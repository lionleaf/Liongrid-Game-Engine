package com.liongrid.gameengine;

import android.view.MotionEvent;

public abstract class InputHUD {

	public abstract boolean onSingleTapUp(MotionEvent event);
	public abstract boolean onDown(MotionEvent e);
	public abstract boolean onScroll(MotionEvent e1, MotionEvent e2, 
			float distanceX, float distanceY);
	
}
