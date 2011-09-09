package com.liongrid.thumbfighter.components;

import android.os.Vibrator;

import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.thumbfighter.TGameActivity;
import com.liongrid.thumbfighter.TGameObject;
import com.liongrid.thumbfighter.TGamePointers;
import com.liongrid.thumbfighter.TPlayerID;

public class TRemoveOutsideComponent extends LComponent<TGameObject> {

	private Vibrator mVibrator;
	public TRemoveOutsideComponent() {
		mVibrator = (Vibrator)TGamePointers.gameActivity.
					getSystemService(TGameActivity.VIBRATOR_SERVICE);
	}
	
	@Override
	public void update(float dt, TGameObject parent) {
		if((parent.team == TPlayerID.player2 && parent.pos.y < 0) || 
			(parent.team == TPlayerID.player1 && 
					parent.pos.y > LGamePointers.panel.getHeight()-parent.heigth)){
			TGamePointers.gameObjectHandler.remove(parent);
			TGamePointers.gameStatus.explosion(parent.pos.y);
			mVibrator.vibrate(400);
		}
		
	}

}
