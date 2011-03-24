package com.infectosaurus.components;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.infectosaurus.GameObject;

public class TestComponent extends Component {
	GameObject gameObject;
	private long lastTime = -1;
	Random random;
	int width = 400; //Screw real data, let`s guess
	int height = 700;
	
	
	public TestComponent(GameObject gameObject) {
		this.gameObject = gameObject;
		random = new Random();
	}
	
	
	@Override
	public void update4Game(float dt) {
	//Test Movement:	
		int randomSpeedX = random.nextInt(200)+35;
		int randomSpeedY = random.nextInt(200)+35;
		
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
		
		if(lastTime == -1){
			lastTime = System.nanoTime();
			return;
		}
		long cTime = System.nanoTime();
		dt = cTime - lastTime;
		
		
		dt = (float) (dt * 0.000000001);
		lastTime = cTime;
		gameObject.posX = gameObject.posX + gameObject.velX * dt;
		gameObject.posY = gameObject.posY + gameObject.velY * dt;
		
	}

	@Override
	public void update4Renderer(GL10 gl) {
		// TODO Auto-generated method stub
		
	}

}
