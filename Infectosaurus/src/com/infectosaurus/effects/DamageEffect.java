package com.infectosaurus.effects;

import android.util.Log;

import com.infectosaurus.BaseObject;
import com.infectosaurus.GameObject;

public class DamageEffect extends Effect {
	
	private int damage;
	
	public DamageEffect(){
		
	}
	
	public void set(int damage){
		this.damage = damage;
	}
	
	public void update(float dt, GameObject target, GameObject afflictor){
		target.hp -= damage;
		Log.d("DamageEffect", "Damage dealt");
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
