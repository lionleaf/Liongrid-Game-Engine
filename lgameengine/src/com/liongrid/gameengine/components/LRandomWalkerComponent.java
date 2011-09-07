package com.liongrid.gameengine.components;

import java.util.Random;

import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.tools.LVector2;

public class LRandomWalkerComponent extends LComponent{
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
	
	public LRandomWalkerComponent(){
		random = new Random();
		
	}

	@Override
	public void update(float dt, LGameObject parent) {
		width = LGamePointers.panel.getWidth();
		height = LGamePointers.panel.getHeight();
		if(width <= 0 || height <= 0) return;
		

		vel = parent.vel;
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
			float speed = vel.length();
			vel.set(goal);
			vel.subtract(pos);
			vel.normalize();
			vel.multiply(speed);
			recalculate = false;
		}
		
	}

}
