package com.infectosaurus.components;

import java.util.Random;

import com.infectosaurus.BaseObject;
import com.infectosaurus.GameObject;

public class RandomWalkerComponent extends Component{
	private GameObject gameObject;
	Random random;
	public RandomWalkerComponent(GameObject gameObject) {
		this.gameObject = gameObject;
		random = new Random();
	}
	@Override
	public void update(float dt, BaseObject parent) {
		float i;
		float n = 0.01f;
		i = random.nextFloat();
		i -= 0.5f;
		gameObject.posX += i;
		i = random.nextFloat();
		i -= 0.5f;
		gameObject.posY += i;
		
	}

}
