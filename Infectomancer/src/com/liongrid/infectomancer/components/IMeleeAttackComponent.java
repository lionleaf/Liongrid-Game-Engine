package com.liongrid.infectomancer.components;

import android.util.Log;

import com.liongrid.gameengine.LAnimationCodes;
import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LSoundSystem;
import com.liongrid.gameengine.components.LSpriteComponent;
import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.infectomancer.IGameObject;
import com.liongrid.infectomancer.IGameObjectHandler;
import com.liongrid.infectomancer.IGamePointers;
import com.liongrid.infectomancer.ITeam;
import com.liongrid.infectomancer.effects.IInfectedDamageEffect;
import com.liongrid.infectomancer.effects.ISpeedBuffEffect;


public class IMeleeAttackComponent extends LComponent {

	IGameObjectHandler gameObjHandler;
	static final int CLOSE_CAPACITY = 20;
	LFixedSizeArray<IGameObject> close;
	private int mReach = 15 * 15; //Square of the actual mReach 100 means 10 px
	private int mDamage = 1;
	private float mDelay = 2f; //sec
	private float mDelayCountDown = 0;
	private float mInfectChance = 0f;
	private boolean mEnabled = true;
	private boolean mInfect = true;
	private LSoundSystem.Sound mHitSound;
	
	public IMeleeAttackComponent(){
		super();
		close = new LFixedSizeArray<IGameObject>(CLOSE_CAPACITY);
		set();
		
	}
	
	public void setHitSound(LSoundSystem.Sound hitSound){
		mHitSound = hitSound;
	}
	
	public void set(){
		gameObjHandler = IGamePointers.gameObjectHandler;
	}
	
	public void setReach(int reach){
		mReach = reach * reach;
	}
	
	public int getReach(){
		return (int) Math.sqrt(mReach);
	}
	
	public void addToDamage(int value){
		mDamage += value;
	}
	
	@Override
	public void update(float dt, LGameObject owner) {
		if(!mEnabled) return;
		IGameObject parent = (IGameObject) owner;
		mDelayCountDown -= dt;
		if(mDelayCountDown > 0) return;
		
		ITeam team = parent.team == ITeam.Human ? ITeam.Alien: ITeam.Human;
		IGameObject target = (IGameObject) gameObjHandler.getClosest(parent.pos, team, parent, true);
		gameObjHandler.getClosestOld(parent.pos, team, parent);
		
		if(target == null || 
				target.distance2(parent) > mReach){
			return;
		}
			
		playHitSound();
		
		mDelayCountDown = mDelay;
		
		
		//TODO pool this!!
		
		IInfectedDamageEffect eff = new IInfectedDamageEffect();
		eff.set(mDamage, mInfect ? mInfectChance : 0f);
		target.afflict(eff);
		
		ISpeedBuffEffect speed = new ISpeedBuffEffect();
		speed.set(1f, 1, 100);
		target.afflict(speed);
		
		
		
		LSpriteComponent spr = (LSpriteComponent) parent.findComponentOfType(LSpriteComponent.class);
		if(spr == null) {
			Log.e("Infectosaurus", "Could not find LSpriteComponent... that`s odd");
			return;
		}
		
		spr.setOverlayAnimation(LAnimationCodes.ATTACK_EAST);
		
	}
	
	private void playHitSound(){
		if(mHitSound == null) return;
		LSoundSystem sSystem = LGamePointers.soundSystem;
		sSystem.play(mHitSound, false, LSoundSystem.PRIORITY_NORMAL);
	}
	

	/**
	 * Sets the infect chance
	 * @param infectChance - 1 = 100% infect chance.
	 */
	public void setInfectChance(float infectChance) {
		this.mInfectChance = infectChance;
	}

	public void setDelay(float delay){
		mDelay = delay;
	}
	
	public void addToReach(int reach) {
		mReach = (int) ((Math.sqrt(mReach)+reach)*(Math.sqrt(mReach)+reach));
	}

	public void setEnabled(boolean value) {
		mEnabled = value;
		
	}

	public void setInfect(boolean b) {
		mInfect = b; 
		
	}
}
