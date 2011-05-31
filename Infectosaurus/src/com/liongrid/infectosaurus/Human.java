package com.liongrid.infectosaurus;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.GameObject;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.BehaviorComponent;
import com.liongrid.infectosaurus.components.MoveComponent;
import com.liongrid.infectosaurus.components.RandomWalkerComponent;
import com.liongrid.infectosaurus.components.SpriteComponent;

public class Human extends InfectoGameObject{
	static Random rand = new Random();
	public Human() {
		TextureLibrary texLib = gamePointers.textureLib;
		DrawableBitmap db = new DrawableBitmap(
				texLib.allocateTexture(R.drawable.mann1), 16*3, 16*3);
		addComponent(new SpriteComponent(db)); 
		addComponent(new BehaviorComponent());
		
		speed = rand.nextInt(20)+10;
		
		int width = gamePointers.panel.getWidth();
		int height = gamePointers.panel.getHeight();
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
