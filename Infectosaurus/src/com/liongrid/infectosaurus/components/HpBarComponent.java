package com.liongrid.infectosaurus.components;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.DrawableObject;
import com.liongrid.gameengine.RenderElement;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.R;

public class HpBarComponent extends Component<InfectoGameObject> {
	DrawableBitmap mBarBackground;
	DrawableBitmap mBarForeground;
	int mHeight = 10;
	int mWidth = 64;
	
	public HpBarComponent() {
		mBarBackground = new DrawableBitmap
		(gamePointers.textureLib.allocateTexture(R.drawable.red),mWidth,mHeight,false);
		mBarForeground = new DrawableBitmap
		(gamePointers.textureLib.allocateTexture(R.drawable.green),0,mHeight,false);
		
	}
	
	@Override
	public void update(float dt, InfectoGameObject parent) {
		 mBarForeground.setWidth(
				 (int)Math.round(mWidth*(parent.mHp/(float)parent.mMaxHp)));
		
		 
		 SpriteComponent sprite = (SpriteComponent) parent.findComponentOfType(SpriteComponent.class);
		 
		 if(sprite == null) return;
		 
		 int spriteHeight = sprite.lastDrawing.getHeight();
		 
		 gamePointers.renderSystem.scheduleForDraw(mBarBackground, parent.pos.x-0.5f*mWidth, parent.pos.y+0.5f*spriteHeight);
		 gamePointers.renderSystem.scheduleForDraw(mBarForeground,  parent.pos.x-0.5f*mWidth, parent.pos.y+0.5f*spriteHeight);
	}

}
