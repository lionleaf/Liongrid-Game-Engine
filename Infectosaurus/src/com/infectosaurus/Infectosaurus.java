package com.infectosaurus;

import com.infectosaurus.components.AnimationComponent;
import com.infectosaurus.components.MeleeAttackComponent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Infectosaurus extends GameObject {
	private static final String TAG = "GameBoard";
	private Bitmap mBitmap;
	private Panel mPanel;

	Infectosaurus(Panel panel) {
		mBitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.lumberinghulklo);
		addGameComponent(new MeleeAttackComponent());
		addGameComponent(new AnimationComponent(this));
		mPanel = panel;
	}
	

	public Panel getPanel() {
		return mPanel;
	}


	public Bitmap getBitmap() {
		return mBitmap;
	}
}
