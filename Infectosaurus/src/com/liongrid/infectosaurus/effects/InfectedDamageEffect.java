package com.liongrid.infectosaurus.effects;

import java.util.Random;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Effect;
import com.liongrid.gameengine.GameObject;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.Infectosaurus;

public class InfectedDamageEffect extends Effect<InfectoGameObject> {
	
	private int damage;
	private float infectChance = 33f/100;
	private static Random random = new Random();
	
	public InfectedDamageEffect(){
		
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
		if(target.infectable && target.hp <= 0){
			float check = random.nextFloat();
			if(check < infectChance){
				Infectosaurus inf = new Infectosaurus();
				inf.pos.set(target.pos);
				GameActivity.infectoPointers.gameObjectHandler.add(inf);
			}
		}
	}

	@Override
	public void onRemove(InfectoGameObject target) {
		
	}



}
