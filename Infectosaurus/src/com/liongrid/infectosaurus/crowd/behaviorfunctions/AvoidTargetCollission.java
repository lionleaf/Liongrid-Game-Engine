package com.liongrid.infectosaurus.crowd.behaviorfunctions;

import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.crowd.State;
import com.liongrid.infectosaurus.crowd.StateList;

public class AvoidTargetCollission extends BehaviorFunction{

	private Vector2 vec = new Vector2();

	@Override
	protected float evaluate(State s, StateList lastStates) {
//		InfectoGameObjectHandler goh = 
//			GameActivity.infectoPointers.gameObjectHandler;
//		
//		InfectoGameObject target = goh.getClosest(s.parent, s.pos, Team.All);
//		if(s.pos.distance2(target.pos) > s.parent.hitbox) return 0;
//		
//		vec.set(target.pos);
//		vec.subtract(lastStates.get(1).pos);
//		float angToInf = vec.getAngle();
//		
//		//Angle of velocity towards infectosaurus. 0 is straight on. Pi is away
//		float lastVelAngle = lastStates.get(1).vel.getAngle() - angToInf;
//		float newVelAngle = s.vel.getAngle() - angToInf;
//		
//		//Get rid of negative angles:
//		lastVelAngle = Math.abs(lastVelAngle);
//		newVelAngle = Math.abs(newVelAngle);
//		
//		//Choose the smallest angle
//		if(lastVelAngle > Math.PI){
//			lastVelAngle = (float) (2*Math.PI - lastVelAngle);
//		}
//		if(newVelAngle > Math.PI){
//			newVelAngle  = (float) (2*Math.PI - newVelAngle);
//		}
//		
//		if(newVelAngle < lastVelAngle){
//			return -5;
//		}else if(newVelAngle == lastVelAngle){
//			return 2;
//		}else{//Better outcome!
//			return 5;
//		}
		return INDIFFERENT;
	}
}
