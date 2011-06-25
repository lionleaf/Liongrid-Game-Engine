package com.liongrid.infectosaurus;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Collision;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.GameObject;
import com.liongrid.gameengine.Panel;
import com.liongrid.gameengine.Texture;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.gameengine.Upgrade;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.AggressivMoveComponent;
import com.liongrid.infectosaurus.components.LAnimation;
import com.liongrid.infectosaurus.components.InfMeleeAttackComponent;
import com.liongrid.infectosaurus.components.MoveComponent;
import com.liongrid.infectosaurus.components.RandomWalkerComponent;
import com.liongrid.infectosaurus.components.SpriteComponent;
import com.liongrid.infectosaurus.components.SpriteComponent.SpriteState;
import com.liongrid.infectosaurus.effects.DelayedDamageEffect;
import com.liongrid.infectosaurus.upgrades.InfectosaurusUpgrade;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * @author Liongrid
 *	All the extra functionality should instead 
 *	be added to a component?
 */
public class Infectosaurus extends InfectoGameObject {
	
	public Infectosaurus() {
		Log.d(Main.TAG, "Infectosaurus construct");
		Panel panel = BaseObject.gamePointers.panel;
		
		
		TextureLibrary texLib = gamePointers.textureLib;
		DrawableBitmap[] dbs = new DrawableBitmap[4];
		
		Texture tex = texLib.allocateTexture(R.drawable.spheremonster01);
		int size = 16*3;
		radius = (float) (size/2.0);
		dbs[0] = new DrawableBitmap(tex, size, size);
		dbs[1] = new DrawableBitmap(tex, size+3, size+3);
		dbs[2] = new DrawableBitmap(tex, size+6, size+6);
		dbs[3] = new DrawableBitmap(tex, size+3, size+3);
		
		DrawableBitmap[] attackBmps = new DrawableBitmap[1];
		
		attackBmps[0] = new DrawableBitmap(tex, 16*3+25, 16*3+25);
		
		LAnimation moveAnimation = new LAnimation(dbs, 0.1f);
		LAnimation attackAnimation = new LAnimation(attackBmps, 0.1f, false);
		
		SpriteComponent sprite = new SpriteComponent();
		sprite.setAnimation(SpriteState.idle, moveAnimation);
		sprite.setAnimation(SpriteState.attacking, attackAnimation);
		
		
		
		addComponent(new InfMeleeAttackComponent());
		addComponent(new AggressivMoveComponent());
		addComponent(sprite);
		addComponent(new MoveComponent());
		
		speed = 80;
		
		team = Team.Alien;
		
		
		//Temp stuff to die in x sec
		DelayedDamageEffect e = new DelayedDamageEffect();
		e.set(20,500);
		afflict(e);
		
		
		
		applyUpgrades();
		
	}
	
	private void applyUpgrades() {
		InfectosaurusUpgrade[] us = InfectosaurusUpgrade.values();
		
		int len = us.length;
		
		for(int i = 0; i < len; i++){
			us[i].get().apply(this);
		}
		
	}
	
	@Override
	public void collide(InfectoGameObject o) {
		if(Collision.collides(this, o)){
			if(o.pos.x < pos.x) pos.x = (float) 
				(o.pos.x + pos.x/(Math.sqrt(o.pos.distance2(pos))));
			else {
				pos.x = (float) 
				(o.pos.x - pos.x/(Math.sqrt(o.pos.distance2(pos))));
			}
			
			if(o.pos.y < pos.y) pos.y = (float) 
				(o.pos.y + pos.y/(Math.sqrt(o.pos.distance2(pos))));
			else {
				pos.y = (float) 
				(o.pos.y - pos.y/(Math.sqrt(o.pos.distance2(pos))));
			}
		}
	}

	@Override
	protected void die() {
		super.die();
		gamePointers.currentSaurus = null;
	}
}
