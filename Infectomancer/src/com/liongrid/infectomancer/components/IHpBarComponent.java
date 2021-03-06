package com.liongrid.infectomancer.components;

import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.components.LSpriteComponent;
import com.liongrid.infectomancer.IGameObject;
import com.liongrid.infectomancer.R;

public class IHpBarComponent extends LComponent<IGameObject> {
	LDrawableBitmap mBarBackground;
	LDrawableBitmap mBarForeground;
	int mHeight = 10;
	int mWidth = 64;
	
	public IHpBarComponent() {
		mBarBackground = new LDrawableBitmap
		(LGamePointers.textureLib.allocateTexture(R.drawable.red),mWidth,mHeight);
		mBarForeground = new LDrawableBitmap
		(LGamePointers.textureLib.allocateTexture(R.drawable.green),0,mHeight);
		
	}
	
	@Override
	public void update(float dt, IGameObject parent) {
		float width =  mWidth*(parent.mHp/(float)parent.mMaxHp);
		width = width < 0? 0 : width;
		mBarForeground.setWidth(Math.round(width));
		
		 
		 LSpriteComponent sprite = (LSpriteComponent) parent.findComponentOfType(LSpriteComponent.class);
		 
		 if(sprite == null) return;
		 
		 int spriteHeight = sprite.getLastDrawing().getHeight();
		 
		 LGamePointers.renderSystem.scheduleForDraw(
				 mBarBackground, parent.pos.x-0.5f*mWidth, parent.pos.y+0.5f*spriteHeight, false);
		 LGamePointers.renderSystem.scheduleForDraw(
				 mBarForeground,  parent.pos.x-0.5f*mWidth, parent.pos.y+0.5f*spriteHeight, false);
	}

}
