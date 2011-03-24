package com.infectosaurus.components;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import com.infectosaurus.GameObject;

public class RandomWalkerComponent extends Component{
	private GameObject gameObject;

	public RandomWalkerComponent(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	@Override
	public void update4Game() {
		Random random = new Random();
		int i;
		int n = 10;
		i = random.nextInt(n);
		i -= n/2;
		gameObject.posX += i;
		i = random.nextInt(n);
		i -= n/2;
		gameObject.posY += i;
		
	}

	@Override
	public void update4Renderer(GL10 gl) {
		// TODO Auto-generated method stub
		
	}

}
