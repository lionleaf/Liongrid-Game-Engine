package com.liongrid.gameengine;

public class LDrawableComponent extends LComponent {

	LDrawableBitmap mBitmap;
	
	public LDrawableComponent(LDrawableBitmap bitmap) {
		mBitmap = bitmap;
	}
	
	@Override
	public void update(float dt, LGameObject parent) {
		LGamePointers.renderSystem.scheduleForDraw(mBitmap, parent.mPos.x,
				parent.mPos.x,false);
	}

}
