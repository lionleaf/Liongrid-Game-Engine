package com.infectosaurus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.infectosaurus.components.AnimationComponent;

public class Human extends GameObject{

	public Human(Panel panel) {
		Bitmap mBitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.sheeplo);
		addRenderComponent(new AnimationComponent(this, mBitmap));
		posX = 50;
		
	}

}
