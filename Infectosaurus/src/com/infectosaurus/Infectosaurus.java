package com.infectosaurus;

import com.infectosaurus.components.AnimationComponent;
import com.infectosaurus.components.MeleeAttackComponent;
import com.infectosaurus.components.RandomWalkerComponent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Infectosaurus extends GameObject {
	private static final String TAG = "GameBoard";

	Infectosaurus(Panel panel) {
		Bitmap mBitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.lumberinghulklo);
		addGameComponent(new MeleeAttackComponent());
		addRenderComponent(new AnimationComponent(this, mBitmap));
		addGameComponent(new RandomWalkerComponent(this));
	}
}
