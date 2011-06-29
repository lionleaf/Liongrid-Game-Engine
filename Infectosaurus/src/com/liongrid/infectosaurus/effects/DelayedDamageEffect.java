package com.liongrid.infectosaurus.effects;

import com.liongrid.gameengine.Effect;
import com.liongrid.infectosaurus.InfectoGameObject;

public class DelayedDamageEffect extends Effect<InfectoGameObject> {

	private int dmg = 0;

	public void set(float duration, int damage){
		super.set(duration);
		this.dmg  = damage;
	}
	
	
	@Override
	public void tick(float dt, InfectoGameObject parent) {
		// We wait, so do nothing
		
	}

	@Override
	public void onApply(InfectoGameObject target) {
		// Do nothing
		
	}

	@Override
	public void onRemove(InfectoGameObject target) {
		//Time to roll!
		
		target.mHp -= dmg;
		
	}

}
