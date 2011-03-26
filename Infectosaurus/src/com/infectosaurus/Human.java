package com.infectosaurus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.infectosaurus.components.RandomWalkerComponent;
import com.infectosaurus.components.SpriteComponent;
import com.infectosaurus.components.TestComponent;

public class Human extends GameObject{

	public Human() {
		Panel panel = BaseObject.gamePointers.panel;
		
		Bitmap mBitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.sheeplo);
		addComponent(new SpriteComponent(new DrawableBitmap(mBitmap,200,200)));
		addComponent(new TestComponent());
	}
}
