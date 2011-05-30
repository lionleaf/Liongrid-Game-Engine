package com.liongrid.infectosaurus.effects;

import android.util.Log;

import com.liongrid.infectosaurus.gameengine.BaseObject;
import com.liongrid.infectosaurus.gameengine.GameObject;

public class DamageEffect extends Effect {
	
	private int damage;
	
	public DamageEffect(){
		
	}
	
	public void set(int damage){
		this.damage = damage;
	}
	
	public void update(float dt, GameObject target, GameObject afflictor){
		target.hp -= damage;
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
