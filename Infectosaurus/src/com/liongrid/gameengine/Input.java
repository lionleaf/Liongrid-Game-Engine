package com.liongrid.gameengine;

import com.liongrid.infectosaurus.Main;

import android.util.Log;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class Input extends SimpleOnGestureListener{

	private InputDispatchInterface mTopLayer;
	private InputDispatchInterface mBottomLayer;

	public Input(InputDispatchInterface topLayer, InputDispatchInterface bottomLayer) {
		this.mTopLayer = topLayer;
		this.mBottomLayer = bottomLayer;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		if(mTopLayer.dispatchSingleTapUp(event)) return true;
		if(mBottomLayer.dispatchSingleTapUp(event)) return true;
		return false;
	}
	
	@Override
	public boolean onDown(MotionEvent e) {
		if(mTopLayer.dispatchTouchDown(e)) return true;
		if(mBottomLayer.dispatchTouchDown(e)) return true;
		return false;
	}
	
	@Override
	public void onLongPress(MotionEvent e) {
		if(mTopLayer.dispatchLongPress(e)) return;
		if(mBottomLayer.dispatchLongPress(e)) return;
	}
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, 
			float distanceX, float distanceY) {
		if(mTopLayer.dispatchScroll(e1, e2, distanceX, distanceY)) return true;
		if(mBottomLayer.dispatchScroll(e1, e2, distanceX, distanceY)) return true;
		return false;
	}
	
	@Override
	public void onShowPress(MotionEvent e) {
		Log.d(Main.TAG, "ShowPress");
		if(mTopLayer.dispatchShowPress(e)) return;
		if(mBottomLayer.dispatchShowPress(e)) return;
	}
}
