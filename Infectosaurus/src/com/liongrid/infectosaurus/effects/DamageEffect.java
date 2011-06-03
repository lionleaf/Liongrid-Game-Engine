package com.liongrid.infectosaurus.effects;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Effect;
import com.liongrid.gameengine.GameObject;
import com.liongrid.infectosaurus.InfectoGameObject;

public class DamageEffect extends Effect<InfectoGameObject> {
	
	private int damage;
	
	public DamageEffect(){
		
	}
	
	/**
	 * @param damage - damage to be done
	 */
	public void set(int damage){
		//instant, so we set duration to 0
		super.set(0);
		
		this.damage = damage;
	}
	
	
	
	public void tick(float dt, InfectoGameObject target){
		
	}
	
	@Override
	public void reset() {
		super.reset();
		
		damage = 0;

	}

	@Override
	public void onApply(InfectoGameObject target) {
		target.hp -= damage;
	}

	@Override
	public void onRemove(InfectoGameObject target) {
		
	}



}
