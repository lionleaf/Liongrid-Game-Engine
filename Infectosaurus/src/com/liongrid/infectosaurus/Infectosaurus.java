package com.liongrid.infectosaurus;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Collision;
import com.liongrid.gameengine.CollisionCircle;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.Panel;
import com.liongrid.gameengine.Texture;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.AggressivMoveComponent;
import com.liongrid.infectosaurus.components.CollisionComponent;
import com.liongrid.infectosaurus.components.HpBarComponent;
import com.liongrid.infectosaurus.components.LionAnimation;
import com.liongrid.infectosaurus.components.InfMeleeAttackComponent;
import com.liongrid.infectosaurus.components.MoveComponent;
import com.liongrid.infectosaurus.components.SpriteComponent;
import com.liongrid.infectosaurus.components.SpriteComponent.SpriteState;
import com.liongrid.infectosaurus.effects.DOTEffect;
import com.liongrid.infectosaurus.upgrades.InfectosaurusUpgrade;


import android.util.Log;

/**
 * @author Liongrid
 *	All the extra functionality should instead 
 *	be added to a component?
 */
public class Infectosaurus extends InfectoGameObject {
	
	private int mSize;
	private SpriteComponent sprite;
	private boolean addedAttack = false;
	InfMeleeAttackComponent mAttackComponent;

	public Infectosaurus() {
		
		mSize = 16*3;
		float radius = (float) (mSize/2.0);
		collisionObject = new CollisionCircle(Team.Alien.ordinal(), this, radius);
		
		TextureLibrary texLib = gamePointers.textureLib;
		Texture tex = texLib.allocateTexture(R.drawable.spheremonster01);
		sprite = loadAnimations(tex);
		sprite.setSpriteState(SpriteState.spawning);
		mAttackComponent = new InfMeleeAttackComponent();
		mAttackComponent.setEnabled(false);
		addComponent(new CollisionComponent());
		addComponent(mAttackComponent);
		addComponent(new AggressivMoveComponent());
		addComponent(sprite);
		addComponent(new MoveComponent());
		addComponent(new HpBarComponent());
		speed = 80;
		
		team = Team.Alien;
		
		int diff = GameActivity.infectoPointers.difficulty;
		//Temp stuff to die in x sec
		DOTEffect e = new DOTEffect();
		e.set(Float.MAX_VALUE, diff, 1f);
		afflict(e);
		
		mMaxHp = 15;
		
		applyUpgrades();
		
	}
	
	@Override
	public void update(float dt, BaseObject parent) {
		//Make it not attack until spawning is done!
		if(!addedAttack && sprite.getSpriteState() != SpriteState.spawning){
			mAttackComponent.setEnabled(true);
			addedAttack = true;
		}
		super.update(dt, parent);
		
	}
	
	private SpriteComponent loadAnimations(Texture tex) {
		SpriteComponent sprite = new SpriteComponent();
		DrawableBitmap[] dbs = new DrawableBitmap[4];
		DrawableBitmap[] attackBmps = new DrawableBitmap[1];
		DrawableBitmap[] spawnBmps = new DrawableBitmap[5];
		
		
		dbs[0] = new DrawableBitmap(tex, mSize,   mSize);
		dbs[1] = new DrawableBitmap(tex, mSize+3, mSize+3);
		dbs[2] = new DrawableBitmap(tex, mSize+6, mSize+6);
		dbs[3] = new DrawableBitmap(tex, mSize+3, mSize+3);
		
		
		spawnBmps[0] = new DrawableBitmap(tex, mSize/6, mSize/6);
		spawnBmps[1] = new DrawableBitmap(tex, 2*mSize/6, 2*mSize/6);
		spawnBmps[2] = new DrawableBitmap(tex, 3*mSize/6, 3*mSize/6);
		spawnBmps[3] = new DrawableBitmap(tex, 4*mSize/6, 4*mSize/6);
		spawnBmps[4] = new DrawableBitmap(tex, 5*mSize/6, 5*mSize/6);
		
		
		attackBmps[0] = new DrawableBitmap(tex, mSize+25, mSize+25);
		
		LionAnimation moveAnimation = new LionAnimation(dbs, 0.1f);
		LionAnimation attackAnimation = new LionAnimation(attackBmps, 0.1f, false);
		LionAnimation spawnAnimation = new LionAnimation(spawnBmps, 0.06f, false);
		
		sprite.setAnimation(SpriteState.idle, moveAnimation);
		sprite.setAnimation(SpriteState.attacking, attackAnimation);
		sprite.setAnimation(SpriteState.spawning, spawnAnimation);
		return sprite;
	}

	private void applyUpgrades() {
		InfectosaurusUpgrade[] us = InfectosaurusUpgrade.values();
		
		int len = us.length;
		
		for(int i = 0; i < len; i++){
			us[i].get().apply(this);
		}
		postApplyUpgrades();
	}
	
	private void postApplyUpgrades(){
		mHp = mMaxHp;
	}
	
	@Override
	protected void die() {
		super.die();
		gamePointers.currentSaurus = null;
	}
}
