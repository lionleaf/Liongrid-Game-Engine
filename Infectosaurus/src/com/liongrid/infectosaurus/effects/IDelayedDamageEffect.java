package com.liongrid.infectosaurus.effects;

import com.liongrid.gameengine.LEffect;
import com.liongrid.infectosaurus.IGameObject;

public class IDelayedDamageEffect extends LEffect<IGameObject> {

	private int dmg = 0;

	public void set(float duration, int damage){
		super.set(duration);
		this.dmg  = damage;
	}
	
	
	@Override
	public void tick(float dt, IGameObject parent) {
		// We wait, so do nothing
		
	}

	@Override
	public void onApply(IGameObject target) {
		// Do nothing
		
	}

	@Override
	public void onRemove(IGameObject target) {
		//Time to roll!
		
		target.mHp -= dmg;
		
	}

}
