package com.liongrid.infectosaurus.components;

import java.util.Random;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.tools.LVector2;
import com.liongrid.infectosaurus.InfectoGameObject;

public class RandomWalkerComponent extends LComponent<InfectoGameObject>{
	Random random;
	int width; //Screw real data, let`s guess
	int height;
	boolean recalculate = true;
	
	LVector2 vel;
	LVector2 pos;
	LVector2 goal = new LVector2();
	LVector2 temp = new LVector2();
	
	float lastDistance = Integer.MAX_VALUE;
	
	private boolean findFirstGoal = true;
	
	public RandomWalkerComponent(){
		random = new Random();
		
	}

	@Override
	public void update(float dt, InfectoGameObject parent) {
		width = LBaseObject.gamePointers.panel.getWidth();
		height = LBaseObject.gamePointers.panel.getHeight();
		if(width <= 0 || height <= 0) return;
		

		vel = parent.mVel;
		pos = parent.pos;
		
		float newDistance = 0f;
		if(!findFirstGoal){
			newDistance = pos.distance2(goal);
		}
		
		if(findFirstGoal || newDistance > lastDistance){
			goal.x = random.nextInt(width);
			goal.y = random.nextInt(height);
			recalculate = true;
		}
		if(!findFirstGoal){
			lastDistance = newDistance;
		}else{
			findFirstGoal = false;
		}
		
		
		if(recalculate){
			//Drunk and late. Is this math correct?
			vel.set(goal);
			vel.subtract(pos);
			vel.normalize();
			vel.multiply(parent.speed);
			recalculate = false;
		}
		
	}

}
