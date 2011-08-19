package com.liongrid.infectosaurus.effects;

import java.util.Random;

import com.liongrid.gameengine.LEffect;
import com.liongrid.infectosaurus.IGameObject;
import com.liongrid.infectosaurus.IGamePointers;

public class IInfectedDamageEffect extends LEffect<IGameObject> {
	
	private int mDamage;
	private float mInfectChance;
	private static Random random = new Random();
	
	public IInfectedDamageEffect(){
		
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
	public void tick(float dt, IGameObject target){
		
	}
	
	@Override
	public void reset() {
		super.reset();
		mDamage = 0;

	}

	@Override
	public void onApply(IGameObject target) {
		//Only spawn if this is the killing blow!
		boolean startedWithHp = target.mHp > 0;
		target.mHp -= mDamage;
		if(target.mHp <= 0 && startedWithHp && target.infectable){
			float check = random.nextFloat();
			if(check <= mInfectChance){
				IGameObject minion = 
					IGamePointers.spawnPool.spawnMinion(target.mPos.x,target.mPos.y);
				IGamePointers.gameObjectHandler.add(minion);
			}
		}
	}

	@Override
	public void onRemove(IGameObject target) {
		
	}



}
