package com.infectosaurus;

import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class InputSystem extends SimpleOnGestureListener{
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		Log.d(Main.TAG, "Im here!");
		Camera.cameraPosition[0] = (int) e2.getX();
		Camera.cameraPosition[1] = (int) e2.getY();
		return super.onFling(e1, e2, velocityX, velocityY);
	}
}
