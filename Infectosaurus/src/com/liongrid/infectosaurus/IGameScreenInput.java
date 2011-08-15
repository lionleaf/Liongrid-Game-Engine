package com.liongrid.infectosaurus;

import android.view.MotionEvent;

import com.liongrid.gameengine.LCamera;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LInputDispatchInterface;
import com.liongrid.gameengine.LMap;

public class IGameScreenInput implements LInputDispatchInterface{
	public boolean dispatchSingleTapUp(MotionEvent event) {
		LGamePointers.gameThread.registerScreenTouch(event);
		return true;
	}

	public boolean dispatchTouchDown(MotionEvent e) {
		return false;
	}

	public boolean dispatchScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		LGamePointers.renderThread.waitDrawingComplete();
		
		//Make sure not to put any value into the camera that 
		//we are not sure are valid
		float tempX = LCamera.pos.x + distanceX / LCamera.scale;
		if(tempX  < 0) LCamera.pos.x = 0;
		else if(tempX + LCamera.screenWidth/LCamera.scale > LMap.sizePx.x) 
			LCamera.pos.x = Math.max(0, 
					LMap.sizePx.x - (int)( LCamera.screenWidth/LCamera.scale));
		else{
			LCamera.pos.x = (int) tempX;
		}
		
		float tempY = LCamera.pos.y - distanceY / LCamera.scale;
		if(tempY  < 0){
			LCamera.pos.y = 0;
		}else if(tempY  + LCamera.screenHeight/LCamera.scale > LMap.sizePx.y){ 
			LCamera.pos.y = Math.max(0, 
					(LMap.sizePx.y) - (int) (LCamera.screenHeight/LCamera.scale));
		}else{
			LCamera.pos.y = (int) tempY;
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
