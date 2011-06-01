package com.liongrid.infectosaurus;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.GameObject;
import com.liongrid.gameengine.Texture;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.AggressivMoveComponent;
import com.liongrid.infectosaurus.components.AnimationComponent;
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
		DrawableBitmap[] dbs = new DrawableBitmap[4];
		
		Texture tex = texLib.allocateTexture(R.drawable.spheremonster01);
		
		dbs[0] = new DrawableBitmap(tex, 16*3, 16*3);
		dbs[1] = new DrawableBitmap(tex, 16*3+5, 16*3+5);
		dbs[2] = new DrawableBitmap(tex, 16*3+10, 16*3+10);
		dbs[3] = new DrawableBitmap(tex, 16*3+5, 16*3+5);
		
		
		addComponent(new AggressivMoveComponent());
		addComponent(new AnimationComponent(dbs, 0.1f));
		addComponent(new MoveComponent());
		speed = 100;
		team = Team.Alien;
	}
}
