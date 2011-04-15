package com.infectosaurus;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.infectosaurus.components.MoveComponent;
import com.infectosaurus.components.RandomWalkerComponent;
import com.infectosaurus.components.SpriteComponent;

public class Human extends GameObject{
	static Random rand = new Random();
	public Human() {
		Panel panel = BaseObject.gamePointers.panel;
		
		DrawableBitmap db = new DrawableBitmap(
				R.drawable.sheeplo,100,100,panel.getContext());
		addComponent(new SpriteComponent(db)); 
		addComponent(new RandomWalkerComponent());
		addComponent(new MoveComponent());
		
		speed = rand.nextInt(200)+30;
	}
	
	//TODO remove! This is only for testing
	@Override
	protected void die() {
		super.die();
		BaseObject.gamePointers.gameObjectHandler.add(new Human());
	}
	
	
}
