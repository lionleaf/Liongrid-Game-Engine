package com.infectosaurus;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

/**
 * @author Lastis
 * This class needs to hold all the GameObjects and sort the 
 * components in a way that they can be done in the right order 
 */
public class GameObjectHandler {
	private static ArrayList<GameObject> gameObjects;

	GameObjectHandler(Panel panel){
		gameObjects.add(new Infectosaurus(panel));
		gameObjects.add(new Human(panel));
	}

	public void update4Renderer(GL10 gl) {
		for(GameObject o: gameObjects) o.useComp4Renderer(gl);
	}
	
	public void update4Game(){
		for(GameObject o: gameObjects) o.useComp4Game();
	}
}
