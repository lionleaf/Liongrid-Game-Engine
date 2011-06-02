package com.liongrid.infectosaurus.effects;

import com.liongrid.gameengine.Effect;
import com.liongrid.infectosaurus.InfectoGameObject;

public class SpeedBuffEffect extends Effect<InfectoGameObject> {
	
	
	private float multiplier = 1;
	private int flatBonus = 0;

	public void set(float duration, float multiplier, int flatBonus) {
		super.set(duration);
		
		this.multiplier = multiplier;
		this.flatBonus = flatBonus;
	}
	
	@Override
	public void onApply(InfectoGameObject target) {
		target.speed *= multiplier;
		target.speed += flatBonus;
		
	}

	@Override
	public void onRemove(InfectoGameObject target) {
		//Do it in reverse order
		target.speed -= flatBonus;
		target.speed /= multiplier;
		
	}

	@Override
	public void tick(float dt, InfectoGameObject parent) {
		//do nothing
	}

}
