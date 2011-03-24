package com.infectosaurus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.infectosaurus.components.AnimationComponent;
import com.infectosaurus.components.RandomWalkerComponent;
import com.infectosaurus.components.TestComponent;

public class Human extends GameObject{

	public Human(Panel panel) {
		Bitmap mBitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.sheeplo);
		addRenderComponent(new AnimationComponent(this, mBitmap));
		addGameComponent(new TestComponent(this));
	}
}
