package com.liongrid.thumbfighter;

import android.view.MotionEvent;

import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LGameThread.TouchEventListener;

public class TTouchEventListener implements TouchEventListener {

	@Override
	public void onTouch(MotionEvent event) {
		
		int actionCode = event.getAction() & MotionEvent.ACTION_MASK;
		if(!(actionCode == MotionEvent.ACTION_DOWN) && 
			!(actionCode == MotionEvent.ACTION_POINTER_DOWN )){
			return;
		}
		
		float y = event.getY(event.getActionIndex());
		float x = event.getX(event.getActionIndex());
		TPlayerID pID;
		if(y < LGamePointers.panel.getHeight()/2.0){
			pID = TPlayerID.player2; 
		}else{
			pID = TPlayerID.player1;
		}
		TGameObject rocket = TGamePointers.spawnPool.spawnRocket(500, x, pID);
		TGamePointers.gameObjectHandler.add(rocket);

	}

}
