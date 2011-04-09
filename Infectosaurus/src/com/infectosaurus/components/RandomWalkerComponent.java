package com.infectosaurus.components;

import java.util.Random;

import com.infectosaurus.BaseObject;
import com.infectosaurus.GameObject;

public class RandomWalkerComponent extends Component{
	Random random;
	int width = 250; //Screw real data, let`s guess
	int height = 400;
	
	
	public RandomWalkerComponent(){
		random = new Random();
	}

	@Override
	public void update(float dt, BaseObject parent) {
		int randomSpeedX = random.nextInt(200)+35;
		int randomSpeedY = random.nextInt(200)+35;
		GameObject gameObject = (GameObject) parent;
		if(gameObject.velX == 0 ||
				gameObject.velY == 0){
			gameObject.velX = randomSpeedX;
			gameObject.velY = randomSpeedY;
		}
		
		if(gameObject.posX < 0)
			gameObject.velX = randomSpeedX;
		if(gameObject.posY < 0)
			gameObject.velY = randomSpeedY;
		if(gameObject.posX > width)
			gameObject.velX = -randomSpeedX;
		if(gameObject.posY > height)
			gameObject.velY = -randomSpeedY;
		
		
		gameObject.posX = gameObject.posX + gameObject.velX * dt;
		gameObject.posY = gameObject.posY + gameObject.velY * dt;
		
		
	}

}
