package com.liongrid.infectosaurus;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.GameObject;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.AggressivMoveComponent;
import com.liongrid.infectosaurus.components.MeleeAttackComponent;
import com.liongrid.infectosaurus.components.MoveComponent;
import com.liongrid.infectosaurus.components.RandomWalkerComponent;
import com.liongrid.infectosaurus.components.SpriteComponent;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Infectosaurus extends InfectoGameObject {
	public Infectosaurus() {
		Log.d(Main.TAG, "Infectosaurus construct");
		Panel panel = BaseObject.gamePointers.panel;
		
		addComponent(new MeleeAttackComponent());
		
		TextureLibrary texLib = gamePointers.textureLib;
		DrawableBitmap db = new DrawableBitmap(
				texLib.allocateTexture(R.drawable.spheremonster01), 16*3, 16*3);
		
		addComponent(new AggressivMoveComponent());
		addComponent(new SpriteComponent(db));
		addComponent(new MoveComponent());
		speed = 100;
		team = Team.Alien;
	}
}
