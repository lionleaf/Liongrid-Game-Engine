package com.infectosaurus.components;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.infectosaurus.GameObject;

public class RandomWalkerComponent extends Component{
	private GameObject gameObject;
	Random random;
	public RandomWalkerComponent(GameObject gameObject) {
		this.gameObject = gameObject;
		random = new Random();
	}

	@Override
	public void update4Game(float dt) {
		float i;
		float n = 0.01f;
		i = random.nextFloat();
		i -= 0.5f;
		gameObject.posX += i;
		i = random.nextFloat();
		i -= 0.5f;
		gameObject.posY += i;
	}

	@Override
	public void update4Renderer(GL10 gl) {
		// TODO Auto-generated method stub
		
	}

}
