package com.infectosaurus;

import com.infectosaurus.GameObject.Team;

/**
 * @author Lastis
 * This class needs to hold all the GameObjects and sort the 
 * components in a way that they can be done in the right order 
 */
public class GameObjectHandler extends ObjectHandler<GameObject> {
	private static final int DEFAULT_CAPACITY = 256;
	
	static final int NUMBER_OF_HUMANS = 20;
	GameObjectHandler(){
		super(DEFAULT_CAPACITY);
		Human human;
		for (int i = 0; i < NUMBER_OF_HUMANS; i++) {
			human = new Human();
			human.pos.set(0,0);
			objects.add(new Human());
		}
	}
	
	
	
	/**
	 * Finds all gameobjects within reach of gObject
	 * 
	 * @param gObject - The object who wants something close
	 * @param reach - How far you want to search
	 * @return - A FixedSizeArray with the results. (Remember to release it!) 
	 * Or null if there is nothing within reach. It will not return itself.
	 */ 
	public FixedSizeArray<GameObject> getClose(GameObject gObject, int reach, Team team) {
		int length = objects.getCount();
		Object[] objectArr = objects.getArray();
		
		//TODO pool object arrays?!
		FixedSizeArray<GameObject> returnObjects = new FixedSizeArray<GameObject>(length);
		
		for (int i = 0; i < length; i++) {
			GameObject currentObject = (GameObject) objectArr[i];
			
			if(currentObject.pos.distance2(gObject.pos) <= reach 
					&& currentObject != gObject
					&& currentObject.team != team){
				returnObjects.add(currentObject);
			}
		}
		
		if(returnObjects.getCount() > 0) return returnObjects;
		return null;
		
	}
	
	/**
	 * These are quite expensive and scale a lot with complexity, not good!
	 * @param gObject
	 * @param team
	 * @return
	 */
	public GameObject getClosest(GameObject gObject, Team team){
		
		GameObject closest = null;
		float closestDistance = Float.MAX_VALUE;
		float currentDistance = 0;
		
		final int length = objects.getCount();
		
		Object[] objectArr = objects.getArray();
		
		for (int i = 0; i < length; i++) {
			GameObject currentObject = (GameObject) objectArr[i];
			
			if(currentObject.team != team) continue;
			
			currentDistance = currentObject.pos.distance2(gObject.pos);
			
			if(currentDistance < closestDistance && objectArr[i] != gObject){
				
				closestDistance = currentDistance;
				closest = currentObject;
			}
		}
		
		return closest;
	}
	
}
