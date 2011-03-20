package com.infectosaurus;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Infectosaurus extends GameObject{
	static Resources res;
	static final Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.lumberinghulklo);;
	Infectosaurus(Panel panel){
		res = panel.getResources();
		addGameComponent(new MeleeAttackComponent());
	}
}
