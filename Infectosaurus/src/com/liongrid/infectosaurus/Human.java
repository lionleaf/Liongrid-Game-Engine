package com.liongrid.infectosaurus;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.BehaviorComponent;
import com.liongrid.infectosaurus.components.MoveComponent;
import com.liongrid.infectosaurus.components.RandomWalkerComponent;
import com.liongrid.infectosaurus.components.SpriteComponent;
import com.liongrid.infectosaurus.gameengine.BaseObject;
import com.liongrid.infectosaurus.gameengine.DrawableBitmap;
import com.liongrid.infectosaurus.gameengine.GameObject;

public class Human extends GameObject{
	static Random rand = new Random();
	public Human() {
		Panel panel = BaseObject.gamePointers.panel;
		
		DrawableBitmap db = new DrawableBitmap(
				R.drawable.mann1, 16*3, 16*3,panel.getContext());
		addComponent(new SpriteComponent(db)); 
		addComponent(new BehaviorComponent());
		
		speed = rand.nextInt(20)+10;
		
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
