package com.liongrid.infectosaurus;

import java.util.Random;

import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.Texture;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.BehaviorComponent;
import com.liongrid.infectosaurus.components.HpBarComponent;
import com.liongrid.infectosaurus.components.LAnimation;
import com.liongrid.infectosaurus.components.SpriteComponent;
import com.liongrid.infectosaurus.components.SpriteComponent.SpriteState;

public class Human extends InfectoGameObject{
	static Random rand = new Random();
	public Human() {
		mMaxHp = 10;
		mHp = mMaxHp;
		
		TextureLibrary texLib = gamePointers.textureLib;
		DrawableBitmap[] dbs = new DrawableBitmap[2];
		
		Texture f1 = texLib.allocateTexture(R.drawable.manwalk_s_1);
		Texture f2 = texLib.allocateTexture(R.drawable.manwalk_s_2);
		
		int size = 64;
		radius = (float) (size/2.0);
		dbs[0] = new DrawableBitmap(f1, size, size);
		dbs[1] = new DrawableBitmap(f2, size, size);
		
		LAnimation moveAnimation = new LAnimation(dbs, 0.2f);

		
		SpriteComponent sprite = new SpriteComponent();
		sprite.setAnimation(SpriteState.idle, moveAnimation);
		
		addComponent(sprite); 
		addComponent(new BehaviorComponent());
		addComponent(new HpBarComponent());
		
		speed = rand.nextInt(20)+20;
		
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
	}
	
	
}
