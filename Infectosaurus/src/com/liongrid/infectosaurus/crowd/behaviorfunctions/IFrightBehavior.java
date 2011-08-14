package com.liongrid.infectosaurus.crowd.behaviorfunctions;

import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.tools.LVector2;
import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.crowd.*;
import com.liongrid.infectosaurus.crowd.actions.*;
import com.liongrid.infectosaurus.crowd.IStateList;

public class IFrightBehavior extends IBehaviorFunction {
	Infectosaurus inf = null;
	LVector2 vec = new LVector2();
	float alpha = 10;
	
	@Override
	protected double evaluate(IState s, IStateList lastStates) {
		inf = LGamePointers.currentSaurus;
		if(inf == null) return INDIFFERENT;
		//if(s.action instanceof IStand) return Double.NaN;
		if(inf.pos.distance2(s.pos) > 150*150) return INDIFFERENT;
		if(s.action instanceof IStand) return EXTREMELY_LOW;
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
			return VERY_LOW;
		}else if(newVelAngle == lastVelAngle){
			return HIGH;
		}else{//Better outcome!
			return VERY_HIGH;
		}
		
		
		/*float away = 
			(float)(angToInf > 0 ? angToInf - Math.PI : angToInf + Math.PI);
		//return gauss(s.vel.getAngle(), away, 1f);*/
		
	}

	/* (non-Javadoc)
	 * @see com.liongrid.infectosaurus.crowd.behaviorfunctions.IBehaviorFunction#sigmoid(float)
	 */
	@Override
	protected double sigmoid(double r) {
		// TODO Auto-generated method stub
		return sigmoid(r, alpha);
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
