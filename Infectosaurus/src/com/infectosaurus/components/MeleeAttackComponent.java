package com.infectosaurus.components;

import android.util.Log;

import com.infectosaurus.BaseObject;
import com.infectosaurus.FixedSizeArray;
import com.infectosaurus.GameObject;
import com.infectosaurus.GameObjectHandler;
import com.infectosaurus.effects.DamageEffect;


public class MeleeAttackComponent extends Component {

	GameObjectHandler gameObjHandler;
	static final int CLOSE_CAPACITY = 20;
	FixedSizeArray<GameObject> close;
	int reach = 400;
	int damage = 2;
	float delay = -1; //ms
	float delayCountDown = delay;
	
	
	public MeleeAttackComponent(){
		super();
		close = new FixedSizeArray<GameObject>(CLOSE_CAPACITY);
		set();
		
	}
	
	public void set(){
		gameObjHandler = BaseObject.gamePointers.gameObjectHandler;
	}
	
	@Override
	public void update(float dt, BaseObject parent) {
		
		
		GameObject gObject = (GameObject) parent;
		FixedSizeArray<GameObject> targets = gameObjHandler.getClose(gObject, reach);
		if(targets == null) return;
		
		Log.d("MeleeAttack","Objects nearby!");
		
		final int length = targets.getCount();
		Object[] targetArr = targets.getArray();
		
		GameObject target = null;
		GameObject currentTarget = null;
		
		
		for (int i = 0; i < length; i++) {
			currentTarget = (GameObject) targetArr[i]; 
			if(currentTarget.team != gObject.team){
				target = currentTarget;
				break; //Just take the first guy!
			}
		}
		
		if(target == null) return;
		Log.d("MeleeAttack","Found target!");
		
		delayCountDown -= dt;
		if(delayCountDown > 0) return;
		delayCountDown = delay;
		
		
		//TODO pool this!!
		
		DamageEffect eff = new DamageEffect();
		eff.set(damage);
		target.afflict(eff);
		Log.d("MeleeAttack","Attack dealt!");
		
	}
}
