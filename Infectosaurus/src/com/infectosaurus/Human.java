package com.infectosaurus;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class Human extends GraphicsObject{
	Human(Resources res) {
		super(BitmapFactory.decodeResource(res, R.drawable.sheeplo));
	}
		
}
