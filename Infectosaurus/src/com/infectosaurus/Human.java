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
				R.drawable.mann1, 25, 25,panel.getContext());
		addComponent(new SpriteComponent(db)); 
		addComponent(new RandomWalkerComponent());
		addComponent(new MoveComponent());
		
		
		speed = rand.nextInt(100)+10;
		
		int width = BaseObject.gamePointers.panel.getWidth();
		int height = BaseObject.gamePointers.panel.getHeight();
		if(width <= 0 || height <= 0) return;
		
		pos.x = rand.nextInt(width);
		pos.y = rand.nextInt(height);
		
	}
	
	//TODO remove! This is only for testing
	@Override
	protected void die() {
		super.die();
		BaseObject.gamePointers.gameObjectHandler.add(new Human());
	}
	
	
}
