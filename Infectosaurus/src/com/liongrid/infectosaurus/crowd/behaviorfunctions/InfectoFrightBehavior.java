package com.liongrid.infectosaurus.crowd.behaviorfunctions;

import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.crowd.State;
import com.liongrid.infectosaurus.crowd.StateList;

public class InfectoFrightBehavior extends BehaviorFunction {
	Infectosaurus inf = null;
	static final float nr = (float) ((Math.PI*Math.PI)/8);
	Vector2 vec = new Vector2();
	float alpha = 10;
	
	@Override
	protected float evaluate(State s, StateList lastStates) {
		inf = gamePointers.currentSaurus;
		if(inf == null) return 0;
		
		vec.set(inf.pos);
		vec.subtract(lastStates.get(1).pos);
		float angToInf = vec.getAngle();
		
		//Angle of velocity towards infectosaurus. 0 is straight on. Pi is away
		float lastVelAngle = lastStates.get(1).vel.getAngle() - angToInf;
		float newVelAngle = s.vel.getAngle() - angToInf;
		
		//Get rid of negative angles:
		lastVelAngle = Math.abs(lastVelAngle);
		newVelAngle = Math.abs(newVelAngle);
		
		//Choose the smallest angle
		if(lastVelAngle > Math.PI){
			lastVelAngle = (float) (2*Math.PI - lastVelAngle);
		}
		if(newVelAngle > Math.PI){
			newVelAngle  = (float) (2*Math.PI - newVelAngle);
		}
		
		if(newVelAngle < lastVelAngle){
			return -5;
		}else if(newVelAngle == lastVelAngle){
			return 2;
		}else{//Better outcome!
			return 5;
		}
		
		
		/*float away = 
			(float)(angToInf > 0 ? angToInf - Math.PI : angToInf + Math.PI);
		//return gauss(s.vel.getAngle(), away, 1f);*/
		
	}

	/* (non-Javadoc)
	 * @see com.liongrid.infectosaurus.crowd.behaviorfunctions.BehaviorFunction#sigmoid(float)
	 */
	@Override
	protected float sigmoid(float r) {
		// TODO Auto-generated method stub
		return sigmoid(r, alpha);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
