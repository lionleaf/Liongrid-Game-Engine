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
import com.liongrid.infectosaurus.components.SpriteComponent.SpriteState;
import com.liongrid.infectosaurus.effects.InfectedDamageEffect;
import com.liongrid.infectosaurus.effects.SpeedBuffEffect;


public class InfMeleeAttackComponent extends Component<InfectoGameObject> {

	InfectoGameObjectHandler gameObjHandler;
	static final int CLOSE_CAPACITY = 20;
	FixedSizeArray<InfectoGameObject> close;
	int reach = 100; //Square of the actual reach 100 means 10 px
	int damage = 1;
	float delay = 0.5f; //sec
	float delayCountDown = 0;
	private InfectoGameObject lastTarget = null;
	
	
	public InfMeleeAttackComponent(){
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
				parent, parent.pos, parent.team == Team.Human ? Team.Alien : Team.Human);

		
		if(target == null || 
				target.pos.distance2(parent.pos) > reach){
			return;
		}
		
		lastTarget = target;
		
		
		delayCountDown = delay;
		
		
		//TODO pool this!!
		
		InfectedDamageEffect eff = new InfectedDamageEffect();
		eff.set(damage);
		target.afflict(eff);
		
		SpeedBuffEffect speed = new SpeedBuffEffect();
		speed.set(1f, 1, 100);
		target.afflict(speed);
		
		
		SpriteComponent spr = (SpriteComponent) parent.findComponentOfType(SpriteComponent.class);
		if(spr == null) {
			Log.d("Infectosaurus", "Could not find SpriteComponent... that`s odd");
			return;
		}
		
		spr.currentState = SpriteState.attacking;
		
	}
}