package com.liongrid.infectosaurus.effects;

import com.liongrid.gameengine.LEffect;
import com.liongrid.infectosaurus.InfectoGameObject;

public class DOTEffect extends LEffect<InfectoGameObject> {

	private int mTickDamage = 1;
	private float mTickTime = 1f; 
	
	private float timeAccumulator = 0f;
	
	@Override
	public void tick(float dt, InfectoGameObject target) {
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
	public void onApply(InfectoGameObject target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemove(InfectoGameObject target) {
		// TODO Auto-generated method stub
		
	}


}
