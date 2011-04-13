package com.infectosaurus.components;

import java.util.Random;

import android.util.Log;

import com.infectosaurus.BaseObject;
import com.infectosaurus.GameObject;
import com.infectosaurus.Vector2;

public class RandomWalkerComponent extends Component{
	Random random;
	int width = 250; //Screw real data, let`s guess
	int height = 400;
	boolean recalculate = true;
	
	Vector2 vel;
	Vector2 pos;
	Vector2 goal = new Vector2();
	Vector2 temp = new Vector2();
	
	float lastDistance = Integer.MAX_VALUE;
	
	public RandomWalkerComponent(){
		random = new Random();
		goal.x = random.nextInt(width);
		goal.y = random.nextInt(height);
	}

	@Override
	public void update(float dt, BaseObject parent) {
		GameObject gameObject = (GameObject) parent;
		vel = gameObject.vel;
		pos = gameObject.pos;
		
		float newDistance = pos.distance2(goal);
		
		
		if(newDistance > lastDistance){
			goal.x = random.nextInt(width);
			goal.y = random.nextInt(height);
			recalculate = true;
			
		}
		
		lastDistance = newDistance;
		
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
