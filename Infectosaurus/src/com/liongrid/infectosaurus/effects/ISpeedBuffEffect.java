package com.liongrid.infectosaurus.effects;

import com.liongrid.gameengine.LEffect;
import com.liongrid.infectosaurus.IGameObject;

public class ISpeedBuffEffect extends LEffect<IGameObject> {
	
	
	private float multiplier = 1;
	private int flatBonus = 0;

	public void set(float duration, float multiplier, int flatBonus) {
		super.set(duration);
		
		this.multiplier = multiplier;
		this.flatBonus = flatBonus;
	}
	
	@Override
	public void onApply(IGameObject target) {
		target.speed *= multiplier;
		target.speed += flatBonus;
		
	}

	@Override
	public void onRemove(IGameObject target) {
		//Do it in reverse order
		target.speed -= flatBonus;
		target.speed /= multiplier;
		
	}

	@Override
	public void tick(float dt, IGameObject parent) {
		//do nothing
	}

}
