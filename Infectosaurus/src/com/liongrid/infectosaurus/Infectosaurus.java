package com.liongrid.infectosaurus;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.GameObject;
import com.liongrid.gameengine.Panel;
import com.liongrid.gameengine.Texture;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.AggressivMoveComponent;
import com.liongrid.infectosaurus.components.LAnimation;
import com.liongrid.infectosaurus.components.MeleeAttackComponent;
import com.liongrid.infectosaurus.components.MoveComponent;
import com.liongrid.infectosaurus.components.RandomWalkerComponent;
import com.liongrid.infectosaurus.components.SpriteComponent;
import com.liongrid.infectosaurus.components.SpriteComponent.SpriteState;
import com.liongrid.infectosaurus.effects.DelayedDamageEffect;


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
		dbs[1] = new DrawableBitmap(tex, 16*3+3, 16*3+3);
		dbs[2] = new DrawableBitmap(tex, 16*3+6, 16*3+6);
		dbs[3] = new DrawableBitmap(tex, 16*3+3, 16*3+3);
		
		DrawableBitmap[] attackBmps = new DrawableBitmap[1];
		
		attackBmps[0] = new DrawableBitmap(tex, 16*3+20, 16*3+20);
		
		LAnimation moveAnimation =new LAnimation(dbs, 0.1f);
		LAnimation attackAnimation = new LAnimation(attackBmps, 0.06f, false);
		
		SpriteComponent sprite = new SpriteComponent();
		sprite.setAnimation(SpriteState.idle, moveAnimation);
		sprite.setAnimation(SpriteState.attacking, attackAnimation);
		
		
		
		addComponent(new AggressivMoveComponent());
		addComponent(sprite);
		addComponent(new MoveComponent());
		speed = 100;
		team = Team.Alien;
		
		
		//Temp stuff to die in x sec
		DelayedDamageEffect e = new DelayedDamageEffect();
		e.set(20,500);
		afflict(e);
		
	}
	
	@Override
	protected void die() {
		super.die();
		gamePointers.currentSaurus = null;
	}
}
