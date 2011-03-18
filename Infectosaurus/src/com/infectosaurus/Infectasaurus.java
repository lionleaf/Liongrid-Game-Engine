package com.infectosaurus;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class Infectasaurus extends GraphicsObject{

	Infectasaurus(Resources res) {
		super(BitmapFactory.decodeResource(res, R.drawable.lumberinghulklo));
	}

}
