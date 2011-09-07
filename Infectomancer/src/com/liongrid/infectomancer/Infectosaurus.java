package com.liongrid.infectomancer;

import com.liongrid.gameengine.LAnimationCodes;
import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCamera;
import com.liongrid.gameengine.LCollisionCircle;
import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LAnimation;
import com.liongrid.gameengine.LEasyBitmapCropper;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LTexture;
import com.liongrid.gameengine.LTextureLibrary;
import com.liongrid.gameengine.components.LMoveComponent;
import com.liongrid.gameengine.components.LSpriteComponent;
import com.liongrid.infectomancer.components.IAggressivMoveComponent;
import com.liongrid.infectomancer.components.IAnimationChangeComponent;
import com.liongrid.infectomancer.components.ICollisionComponent;
import com.liongrid.infectomancer.components.IHpBarComponent;
import com.liongrid.infectomancer.components.IMeleeAttackComponent;
import com.liongrid.infectomancer.effects.IDOTEffect;
import com.liongrid.infectomancer.upgrades.IUpgrade;
import com.liongrid.infectomancer.R;

/**
 * @author Liongrid
 *	All the extra functionality should instead 
 *	be added to a component?
 */
public class Infectosaurus extends IGameObject {
	
	private int mSize;
	private LSpriteComponent sprite;
	private boolean addedAttack = false;
	private int unit;
	IMeleeAttackComponent mAttackComponent;

	public Infectosaurus() {
		unit = LCamera.unit;
		mSize = (int) (1.5 * unit); //16*3;
		float radius = (float) (mSize/2.0);
		collisionObject = new LCollisionCircle(ITeam.Alien.ordinal(), this, radius);
		
		LTextureLibrary texLib = LGamePointers.textureLib;
		LTexture tex = texLib.allocateTexture(R.drawable.spheremonster01);
		sprite = loadNewAnimations(texLib);
//		sprite.setOverlayAnimation("Spawning");
		mAttackComponent = new IMeleeAttackComponent();
		mAttackComponent.setEnabled(false);
		addComponent(new ICollisionComponent());
		addComponent(mAttackComponent);
		addComponent(new IAggressivMoveComponent());
		addComponent(sprite);
		addComponent(new LMoveComponent());
		addComponent(new IHpBarComponent());
		addComponent(new IAnimationChangeComponent());
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
	
	private LSpriteComponent loadNewAnimations(LTextureLibrary texLib) {
		LTexture tex = texLib.allocateTexture(R.drawable.reaper);
		LSpriteComponent sprite = new LSpriteComponent();
		LDrawableBitmap[] moveEastBmps = new LDrawableBitmap[3];
		LDrawableBitmap[] moveWestBmps = new LDrawableBitmap[3];
		LDrawableBitmap[] attackEastBmps = new LDrawableBitmap[3];
		
		int[] frame1 = LEasyBitmapCropper.cropWithPos(3, 10, 36, 41);
		int[] frame2 = LEasyBitmapCropper.moveCrop(frame1, 1, 0, 7, 0);
		int[] frame3 = LEasyBitmapCropper.moveCrop(frame1, 2, 0, 7, 0);
		moveEastBmps[0] = new LDrawableBitmap(tex, mSize, mSize, frame1);
		moveEastBmps[1] = new LDrawableBitmap(tex, mSize, mSize, frame2);
		moveEastBmps[2] = new LDrawableBitmap(tex, mSize, mSize, frame3);
		
		frame1 = LEasyBitmapCropper.cropWithPos(5, 53, 38, 84);
		frame2 = LEasyBitmapCropper.moveCrop(frame1, 1, 0, 7, 0);
		frame3 = LEasyBitmapCropper.moveCrop(frame1, 2, 0, 7, 0);
		moveWestBmps[0] = new LDrawableBitmap(tex, mSize, mSize, frame1);
		moveWestBmps[1] = new LDrawableBitmap(tex, mSize, mSize, frame2);
		moveWestBmps[2] = new LDrawableBitmap(tex, mSize, mSize, frame3);
		
		frame1 = LEasyBitmapCropper.cropWithPos(6, 282, 47, 321);
		frame2 = LEasyBitmapCropper.cropWithPos(67, 279, 122, 326);
		frame3 = LEasyBitmapCropper.cropWithPos(146, 279, 180, 324);
		attackEastBmps[0] = new LDrawableBitmap(tex, mSize+10, mSize+10, frame1);
		attackEastBmps[1] = new LDrawableBitmap(tex, mSize+10, mSize+10, frame2);
		attackEastBmps[2] = new LDrawableBitmap(tex, mSize+10, mSize+10, frame3);
		
		LAnimation moveEast = new LAnimation(moveEastBmps, 0.1f, true);
		LAnimation moveWest = new LAnimation(moveWestBmps, 0.1f, true);
		LAnimation attackEast = new LAnimation(attackEastBmps, 0.1f, false);
		
		sprite.addAnimation(LAnimationCodes.WALK_EAST, moveEast);
		sprite.addAnimation(LAnimationCodes.WALK_WEST, moveWest);
		sprite.addAnimation(LAnimationCodes.ATTACK_EAST, attackEast);
		
		return sprite;
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
		IGamePointers.currentSaurus = null;
	}
}
