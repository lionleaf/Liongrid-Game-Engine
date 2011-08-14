package com.liongrid.infectosaurus;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCollisionCircle;
import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LAnimation;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LTexture;
import com.liongrid.gameengine.LTextureLibrary;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.IAggressivMoveComponent;
import com.liongrid.infectosaurus.components.ICollisionComponent;
import com.liongrid.infectosaurus.components.IHpBarComponent;
import com.liongrid.infectosaurus.components.IMeleeAttackComponent;
import com.liongrid.infectosaurus.components.IMoveComponent;
import com.liongrid.infectosaurus.components.ISpriteComponent;
import com.liongrid.infectosaurus.effects.IDOTEffect;
import com.liongrid.infectosaurus.upgrades.IUpgrade;

/**
 * @author Liongrid
 *	All the extra functionality should instead 
 *	be added to a component?
 */
public class Infectosaurus extends IGameObject {
	
	private int mSize;
	private ISpriteComponent sprite;
	private boolean addedAttack = false;
	IMeleeAttackComponent mAttackComponent;

	public Infectosaurus() {
		
		mSize = 16*3;
		float radius = (float) (mSize/2.0);
		collisionObject = new LCollisionCircle(ITeam.Alien.ordinal(), this, radius);
		
		LTextureLibrary texLib = LGamePointers.textureLib;
		LTexture tex = texLib.allocateTexture(R.drawable.spheremonster01);
		sprite = loadAnimations(tex);
		sprite.setOverlayAnimation("Spawning");
		mAttackComponent = new IMeleeAttackComponent();
		mAttackComponent.setEnabled(false);
		addComponent(new ICollisionComponent());
		addComponent(mAttackComponent);
		addComponent(new IAggressivMoveComponent());
		addComponent(sprite);
		addComponent(new IMoveComponent());
		addComponent(new IHpBarComponent());
		speed = 80;
		
		team = ITeam.Alien;
		
		int diff = IGamePointers.difficulty;
		//Temp stuff to die in x sec
		IDOTEffect e = new IDOTEffect();
		e.set(Float.MAX_VALUE, diff, 1f);
		afflict(e);
		
		mMaxHp = 15;
		
		applyUpgrades();
		
	}
	
	@Override
	public void update(float dt, LBaseObject parent) {
		//Make it not attack until spawning is done!
		if(!addedAttack && sprite.getSpriteState() != "Spawning"){
			mAttackComponent.setEnabled(true);
			addedAttack = true;
		}
		super.update(dt, parent);
		
	}
	
	private ISpriteComponent loadAnimations(LTexture tex) {
		ISpriteComponent sprite = new ISpriteComponent();
		LDrawableBitmap[] dbs = new LDrawableBitmap[4];
		LDrawableBitmap[] attackBmps = new LDrawableBitmap[1];
		LDrawableBitmap[] spawnBmps = new LDrawableBitmap[5];
		
		
		dbs[0] = new LDrawableBitmap(tex, mSize,   mSize);
		dbs[1] = new LDrawableBitmap(tex, mSize+3, mSize+3);
		dbs[2] = new LDrawableBitmap(tex, mSize+6, mSize+6);
		dbs[3] = new LDrawableBitmap(tex, mSize+3, mSize+3);
		
		
		spawnBmps[0] = new LDrawableBitmap(tex, mSize/6, mSize/6);
		spawnBmps[1] = new LDrawableBitmap(tex, 2*mSize/6, 2*mSize/6);
		spawnBmps[2] = new LDrawableBitmap(tex, 3*mSize/6, 3*mSize/6);
		spawnBmps[3] = new LDrawableBitmap(tex, 4*mSize/6, 4*mSize/6);
		spawnBmps[4] = new LDrawableBitmap(tex, 5*mSize/6, 5*mSize/6);
		
		
		attackBmps[0] = new LDrawableBitmap(tex, mSize+25, mSize+25);
		
		LAnimation moveAnimation = new LAnimation(dbs, 0.1f, true);
		LAnimation attackAnimation = new LAnimation(attackBmps, 0.1f, false);
		LAnimation spawnAnimation = new LAnimation(spawnBmps, 0.06f, false);
		
		sprite.addAnimation("Move", moveAnimation);
		sprite.addAnimation("Attack", attackAnimation);
		sprite.addAnimation("Spawning", spawnAnimation);
		return sprite;
	}

	private void applyUpgrades() {
		IUpgrade[] us = IUpgrade.values();
		
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
		LGamePointers.currentSaurus = null;
	}
}
