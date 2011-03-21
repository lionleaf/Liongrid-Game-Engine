package com.infectosaurus;

import javax.microedition.khronos.opengles.GL10;

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
	
	@Override
	public void useComp4Renderer(GL10 gl) {
		gl.glPushMatrix();
		gl.glLoadIdentity();
		// paramaters in next line should be given in GameObject, but it's not fixed yet
//		gl.glTranslatef(x,y,z);
	}
}
