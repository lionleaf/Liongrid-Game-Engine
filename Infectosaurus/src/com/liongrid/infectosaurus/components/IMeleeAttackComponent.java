package com.liongrid.infectosaurus.components;

import android.util.Log;

import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.infectosaurus.IGameObjectHandler;
import com.liongrid.infectosaurus.IGamePointers;
import com.liongrid.infectosaurus.ITeam;
import com.liongrid.infectosaurus.IGameObject;
import com.liongrid.infectosaurus.effects.IInfectedDamageEffect;
import com.liongrid.infectosaurus.effects.ISpeedBuffEffect;


public class IMeleeAttackComponent extends LComponent<IGameObject> {

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
	
	public IMeleeAttackComponent(){
		super();
		close = new LFixedSizeArray<IGameObject>(CLOSE_CAPACITY);
		set();
		
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
	public void update(float dt, IGameObject parent) {
		if(!mEnabled) return;
		mDelayCountDown -= dt;
		if(mDelayCountDown > 0) return;
		
		ITeam team = parent.team == ITeam.Human ? ITeam.Alien: ITeam.Human;
		IGameObject target = gameObjHandler.getClosest(parent.mPos, team, parent);
		
		if(target == null || 
				target.distance2(parent) > mReach){
			return;
		}
			
		
		mDelayCountDown = mDelay;
		
		
		//TODO pool this!!
		
		IInfectedDamageEffect eff = new IInfectedDamageEffect();
		eff.set(mDamage, mInfect ? mInfectChance : 0f);
		target.afflict(eff);
		
		ISpeedBuffEffect speed = new ISpeedBuffEffect();
		speed.set(1f, 1, 100);
		target.afflict(speed);
		
		
		ISpriteComponent spr = (ISpriteComponent) parent.findComponentOfType(ISpriteComponent.class);
		if(spr == null) {
			Log.d("Infectosaurus", "Could not find ISpriteComponent... that`s odd");
			return;
		}
		
		spr.setOverlayAnimation("Attack");
		
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
