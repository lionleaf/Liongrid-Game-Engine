package com.liongrid.infectosaurus.components;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.GameObject;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObjectHandler;
import com.liongrid.infectosaurus.Team;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.effects.DamageEffect;


public class MeleeAttackComponent extends Component<InfectoGameObject> {

	InfectoGameObjectHandler gameObjHandler;
	static final int CLOSE_CAPACITY = 20;
	FixedSizeArray<InfectoGameObject> close;
	int reach = 100; //Square of the actual reach 100 means 10 px
	int damage = 1;
	float delay = 0.5f; //sec
	float delayCountDown = 0;
	private InfectoGameObject lastTarget = null;
	
	
	public MeleeAttackComponent(){
		super();
		close = new FixedSizeArray<InfectoGameObject>(CLOSE_CAPACITY);
		set();
		
	}
	
	public void set(){
		gameObjHandler = GameActivity.infectoPointers.gameObjectHandler;
	}
	
	@Override
	public void update(float dt, InfectoGameObject parent) {
		
		delayCountDown -= dt;
		if(delayCountDown > 0) return;
		
		InfectoGameObject target =	gameObjHandler.getClosest(
				parent, parent.team == Team.Human ? Team.Alien : Team.Human);

		
		if(target == null || 
				target.pos.distance2(parent.pos) > reach){
			return;
		}
		
		lastTarget = target;
		
		
		delayCountDown = delay;
		
		
		//TODO pool this!!
		
		DamageEffect eff = new DamageEffect();
		eff.set(damage);
		target.afflict(eff);
		
	}
}
