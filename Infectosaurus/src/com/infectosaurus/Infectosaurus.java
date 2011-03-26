package com.infectosaurus;

import com.infectosaurus.components.AnimationComponent;
import com.infectosaurus.components.MeleeAttackComponent;
import com.infectosaurus.components.RandomWalkerComponent;
import com.infectosaurus.components.SpriteComponent;
import com.infectosaurus.components.TestComponent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Infectosaurus extends GameObject {
	private static final String TAG = "GameBoard";

	Infectosaurus() {
		Log.d("Place", "Infectosaurus construct");
		Panel panel = BaseObject.gamePointers.panel;
		
		Bitmap mBitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.lumberinghulklo);
		addComponent(new MeleeAttackComponent());
		addComponent(new SpriteComponent(new DrawableBitmap(mBitmap,100,100)));
		addComponent(new TestComponent());
		velX = 100;
	}
}
