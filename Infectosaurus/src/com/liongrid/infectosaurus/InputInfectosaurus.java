package com.liongrid.infectosaurus;

import android.view.MotionEvent;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Camera;
import com.liongrid.gameengine.InputDispatchInterface;
import com.liongrid.infectosaurus.map.Map;

public class InputInfectosaurus implements InputDispatchInterface{
	public boolean dispatchSingleTapUp(MotionEvent event) {
		BaseObject.gamePointers.gameThread.registerScreenTouch(event);
		return true;
	}

	public boolean dispatchTouchDown(MotionEvent e) {
		return false;
	}

	public boolean dispatchScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		BaseObject.gamePointers.renderThread.waitDrawingComplete();
		
		//Make sure not to put any value into the camera that 
		//we are not sure are valid
		float tempX = Camera.pos.x + distanceX / Camera.scale;
		if(tempX  < 0) Camera.pos.x = 0;
		else if(tempX + Camera.screenWidth/Camera.scale > Map.sizePx.x) 
			Camera.pos.x = Math.max(0, 
					Map.sizePx.x - (int)( Camera.screenWidth/Camera.scale));
		else{
			Camera.pos.x = (int) tempX;
		}
		
		float tempY = Camera.pos.y - distanceY / Camera.scale;
		if(tempY  < 0){
			Camera.pos.y = 0;
		}else if(tempY  + Camera.screenHeight/Camera.scale > Map.sizePx.y){ 
			Camera.pos.y = Math.max(0, 
					(Map.sizePx.y) - (int) (Camera.screenHeight/Camera.scale));
		}else{
			Camera.pos.y = (int) tempY;
		}
		return true;
	}

	public boolean dispatchLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean dispatchShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
