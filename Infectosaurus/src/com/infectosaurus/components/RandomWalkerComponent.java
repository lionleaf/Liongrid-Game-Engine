package com.infectosaurus.components;

import java.util.Random;

import android.util.Log;

import com.infectosaurus.BaseObject;
import com.infectosaurus.GameObject;
import com.infectosaurus.Vector2;

public class RandomWalkerComponent extends Component{
	Random random;
	int width; //Screw real data, let`s guess
	int height;
	boolean recalculate = true;
	
	Vector2 vel;
	Vector2 pos;
	Vector2 goal = new Vector2();
	Vector2 temp = new Vector2();
	
	float lastDistance = Integer.MAX_VALUE;
	
	private boolean findFirstGoal = true;
	
	public RandomWalkerComponent(){
		random = new Random();
		
	}

	@Override
	public void update(float dt, BaseObject parent) {
		width = BaseObject.gamePointers.panel.getWidth();
		height = BaseObject.gamePointers.panel.getHeight();
		if(width <= 0 || height <= 0) return;
		
		GameObject gameObject = (GameObject) parent;
		vel = gameObject.vel;
		pos = gameObject.pos;
		
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
			vel.multiply(gameObject.speed);
			recalculate = false;
		}
		
	}

}
