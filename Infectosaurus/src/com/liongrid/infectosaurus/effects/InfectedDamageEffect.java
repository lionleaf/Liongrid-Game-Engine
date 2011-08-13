package com.liongrid.infectosaurus.effects;

import java.util.Random;

import android.util.Log;

import com.liongrid.gameengine.LEffect;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.Infectosaurus;

public class InfectedDamageEffect extends LEffect<InfectoGameObject> {
	
	private int mDamage;
	private float mInfectChance;
	private static Random random = new Random();
	
	public InfectedDamageEffect(){
		
	}
	
	/**
	 * @param damage - mDamage to be done
	 * @param infectChance 
	 */
	public void set(int damage, float infectChance){
		//instant, so we set duration to 0
		super.set(0);
		
		this.mInfectChance = infectChance;
		this.mDamage = damage;
	}
	
	
	@Override
	public void tick(float dt, InfectoGameObject target){
		
	}
	
	@Override
	public void reset() {
		super.reset();
		mDamage = 0;

	}

	@Override
	public void onApply(InfectoGameObject target) {
		//Only spawn if this is the killing blow!
		boolean startedWithHp = target.mHp > 0;
		target.mHp -= mDamage;
		if(target.mHp <= 0 && startedWithHp && target.infectable){
			float check = random.nextFloat();
			if(check <= mInfectChance){
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
