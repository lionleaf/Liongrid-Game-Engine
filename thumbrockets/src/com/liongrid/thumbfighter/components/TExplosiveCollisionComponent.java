package com.liongrid.thumbfighter.components;

import android.os.Vibrator;
import android.util.Log;

import com.liongrid.gameengine.LCollisionObject;
import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.thumbfighter.TGameActivity;
import com.liongrid.thumbfighter.TGameObject;
import com.liongrid.thumbfighter.TGamePointers;

public class TExplosiveCollisionComponent extends LComponent<TGameObject> {
	private Vibrator mVibrator;
	public TExplosiveCollisionComponent() {
		mVibrator = (Vibrator)TGamePointers.gameActivity.
					getSystemService(TGameActivity.VIBRATOR_SERVICE);
	}
	
	
	@Override
	public void update(float dt, TGameObject parent) {
		if(parent == null) return;
		
		LCollisionObject collisionObject = parent.hitBox;
		LCollisionObject[] arr = collisionObject.collisions;
		int len = collisionObject.collisionCnt;
		for (int i = 0; i < len; i++) {
			if(arr[i].type != parent.team.ordinal()) explode(parent);
		}

	}

	private void explode(TGameObject parent) {
		TGamePointers.gameObjectHandler.remove(parent);
		mVibrator.vibrate(200);
	}

}
