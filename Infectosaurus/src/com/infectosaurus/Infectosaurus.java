package com.infectosaurus;

import com.infectosaurus.components.MeleeAttackComponent;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Infectosaurus extends GameObject{
	private static final String TAG = "GameBoard";
	Infectosaurus(Panel panel){
		Log.d(TAG, "In Infectosaurus");
		addGameComponent(new MeleeAttackComponent());
	}
}
