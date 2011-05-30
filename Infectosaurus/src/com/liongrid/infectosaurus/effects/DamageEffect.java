package com.liongrid.infectosaurus.effects;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.GameObject;
import com.liongrid.infectosaurus.InfectoGameObject;

public class DamageEffect extends Effect {
	
	private int damage;
	
	public DamageEffect(){
		
	}
	
	public void set(int damage){
		this.damage = damage;
	}
	
	public void update(float dt, InfectoGameObject target, InfectoGameObject afflictor){
		target.hp -= damage;
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
