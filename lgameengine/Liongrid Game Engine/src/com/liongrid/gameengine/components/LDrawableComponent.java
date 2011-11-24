package com.liongrid.gameengine.components;

import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.LGamePointers;

import android.util.Log;

public class LDrawableComponent extends LComponent {

	LDrawableBitmap mBitmap;
	
	public LDrawableComponent(LDrawableBitmap bitmap) {
		mBitmap = bitmap;
	}
	
	@Override
	public void update(float dt, LGameObject parent) {
		LGamePointers.renderSystem.scheduleForDraw(mBitmap, parent.pos.x,
				parent.pos.y,true);
	}

}
