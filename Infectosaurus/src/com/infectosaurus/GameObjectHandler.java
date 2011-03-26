package com.infectosaurus;

/**
 * @author Lastis
 * This class needs to hold all the GameObjects and sort the 
 * components in a way that they can be done in the right order 
 */
public class GameObjectHandler extends ObjectHandler {
	private static final int DEFAULT_CAPACITY = 64;
	GameObjectHandler(){
		objects.add(new Infectosaurus());
		objects.add(new Human());
	}
}
