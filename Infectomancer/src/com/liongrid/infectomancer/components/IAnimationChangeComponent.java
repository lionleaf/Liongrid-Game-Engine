package com.liongrid.infectomancer.components;

import android.util.Log;

import com.liongrid.gameengine.LAnimationCodes;
import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.components.LSpriteComponent;
import com.liongrid.infectomancer.IGameObject;

public class IAnimationChangeComponent extends LComponent<IGameObject>{

	@Override
	public void update(float dt, IGameObject parent) {
		float dx = parent.vel.x;
		float dy = parent.vel.y;
		LSpriteComponent spr = 
			(LSpriteComponent) parent.findComponentOfType(LSpriteComponent.class);
		if(spr == null) {
			Log.d("Infectosaurus", "Could not find LSpriteComponent... that`s odd");
			return;
		}
		float absAB = (float) Math.sqrt(dx * dx + dy * dy);
		float cos = dx / absAB;
		
		String direction = cos > 0 ? LAnimationCodes.WALK_EAST : LAnimationCodes.WALK_WEST; 
		spr.setUnderlyingAnimation(direction);
	}
}
