package com.liongrid.infectomancer.effects;

import com.liongrid.gameengine.LEffect;
import com.liongrid.infectomancer.IGameObject;

public class IDOTEffect extends LEffect<IGameObject> {

	private int mTickDamage = 1;
	private float mTickTime = 1f; 
	
	private float timeAccumulator = 0f;
	
	@Override
	public void tick(float dt, IGameObject target) {
		timeAccumulator += dt;
		
		if(timeAccumulator >= mTickTime){
			target.mHp -= mTickDamage;			
			timeAccumulator = 0f;
		}
		
	}

	public void set(float duration, int damage, float tickTime){
		super.set(duration);
		mTickDamage = damage;
		mTickTime = tickTime;
	}
	
	@Override
	public void onApply(IGameObject target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemove(IGameObject target) {
		// TODO Auto-generated method stub
		
	}


}
