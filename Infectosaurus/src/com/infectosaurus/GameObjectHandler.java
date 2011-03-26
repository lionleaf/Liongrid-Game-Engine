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
	private static int RCounter = 0;
	private static int RSize = -1;
	private static int GCounter = 0;
	private static int GSize = -1;
	private static ArrayList<GameObject> gameObjects;
	private static final int capacity = 10;
	GameObjectHandler(Panel panel){
		gameObjects = new ArrayList<GameObject>(capacity);
		gameObjects.add(new Infectosaurus(panel));
		gameObjects.add(new Human(panel));
	}

	public void update4Renderer(GL10 gl) {
		RSize = gameObjects.size();
		while(RCounter < RSize){
			gameObjects.get(RCounter++).useComp4Renderer(gl);
		}
		RCounter = 0;
	}
	
	public void update4Game(){
		GSize = gameObjects.size();
		while(GCounter < GSize){
			gameObjects.get(GCounter++).useComp4Game();
		}
		GCounter = 0;
	}
	
	public ArrayList<GameObject> getAll(){
		return gameObjects;
	}
}
