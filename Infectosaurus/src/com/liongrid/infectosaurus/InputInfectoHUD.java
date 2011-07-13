package com.liongrid.infectosaurus;

import android.util.Log;
import android.view.MotionEvent;

import com.liongrid.gameengine.Camera;
import com.liongrid.gameengine.HUDButton;
import com.liongrid.gameengine.InputHUD;

public class InputInfectoHUD extends InputHUD{

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		HUDObjectHandler hudObjectHandler = GameActivity.infectoPointers.HUDObjectHandler;
		HUDButton button = hudObjectHandler.getButtonAt(event.getX(), 
				Camera.screenHeight - event.getY());
		if(button == null) return false;
		button.onSingleTapUp();
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean onShowPress(MotionEvent event) {
		HUDObjectHandler hudObjectHandler = GameActivity.infectoPointers.HUDObjectHandler;
		HUDButton button = hudObjectHandler.getButtonAt(event.getX(), 
				Camera.screenHeight - event.getY());
		if(button == null) return false;
		button.onShowPress();
		return true;
	}

}
