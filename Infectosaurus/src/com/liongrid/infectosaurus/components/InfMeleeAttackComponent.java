package com.liongrid.infectosaurus.components;

import android.util.Log;

import com.liongrid.gameengine.CollisionHandlerMultipleArrays;
import com.liongrid.gameengine.CollisionObject;
import com.liongrid.gameengine.Component;
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
	private int mReach = 15 * 15; //Square of the actual mReach 100 means 10 px
	private int mDamage = 1;
	private float mDelay = 2f; //sec
	private float mDelayCountDown = 0;
	private float mInfectChance = 0f;
	private boolean mEnabled = true;
	
	public InfMeleeAttackComponent(){
		super();
		close = new FixedSizeArray<InfectoGameObject>(CLOSE_CAPACITY);
		set();
		
	}
	
	public void set(){
		gameObjHandler = GameActivity.infectoPointers.gameObjectHandler;
	}
	
	public void setReach(int reach){
		mReach = reach * reach;
	}
	
	public int getReach(){
		return (int) Math.sqrt(mReach);
	}
	
	public void addToDamage(int value){
		mDamage += value;
	}
	
	@Override
	public void update(float dt, InfectoGameObject parent) {
		if(!mEnabled) return;
		mDelayCountDown -= dt;
		if(mDelayCountDown > 0) return;
		
		Team team = parent.team == Team.Human ? Team.Alien: Team.Human;
		InfectoGameObject target = gameObjHandler.getClosest(parent.pos, team, parent);
		
		if(target == null || 
				target.distance2(parent) > mReach){
			return;
		}
			
		
		mDelayCountDown = mDelay;
		
		
		//TODO pool this!!
		
		InfectedDamageEffect eff = new InfectedDamageEffect();
		eff.set(mDamage, mInfectChance );
		target.afflict(eff);
		
		SpeedBuffEffect speed = new SpeedBuffEffect();
		speed.set(1f, 1, 100);
		target.afflict(speed);
		
		
		SpriteComponent spr = (SpriteComponent) parent.findComponentOfType(SpriteComponent.class);
		if(spr == null) {
			Log.d("Infectosaurus", "Could not find SpriteComponent... that`s odd");
			return;
		}
		
		spr.setSpriteState(SpriteState.attacking);
		
	}

	/**
	 * Sets the infect chance
	 * @param infectChance - 1 = 100% infect chance.
	 */
	public void setInfectChance(float infectChance) {
		this.mInfectChance = infectChance;
	}

	public void setDelay(float delay){
		mDelay = delay;
	}
	
	public void addToReach(int reach) {
		mReach = (int) ((Math.sqrt(mReach)+reach)*(Math.sqrt(mReach)+reach));
	}

	public void setEnabled(boolean value) {
		mEnabled = value;
		
	}
}
