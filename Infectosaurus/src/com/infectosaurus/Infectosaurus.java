package com.infectosaurus;

import com.infectosaurus.components.AnimationComponent;
import com.infectosaurus.components.MeleeAttackComponent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Infectosaurus extends GameObject {
	private static final String TAG = "GameBoard";

	Infectosaurus(Panel panel) {
		Log.d("GameBoard", "In Infectosaurus");
		Bitmap mBitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.lumberinghulklo);
		addGameComponent(new MeleeAttackComponent());
		addRenderComponent(new AnimationComponent(this, mBitmap));
	}
}
