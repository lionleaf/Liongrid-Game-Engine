package com.liongrid.infectosaurus;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.CollisionHandler;
import com.liongrid.gameengine.IllegalObjectException;
import com.liongrid.gameengine.ObjectHandler;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.gameengine.tools.Vector2;

/**
 * @author Lastis
 * This class needs to hold all the GameObjects and sort the 
 * components in a way that they can be done in the right order 
 */
public class InfectoGameObjectHandler extends ObjectHandler<InfectoGameObject> {
	private static final int DEFAULT_CAPACITY = 256;
	
	static final int NUMBER_OF_HUMANS = 10;

	private CollisionHandler<InfectoGameObject> ca;
	public InfectoGameObjectHandler(){
		super(DEFAULT_CAPACITY);
		Human human;
		ca = new CollisionHandler<InfectoGameObject>
					(Team.values().length, DEFAULT_CAPACITY);
		
		for (int i = 0; i < NUMBER_OF_HUMANS; i++) {
			human = new Human();
			human.pos.set(0,0);
			Human newHuman = new Human();
			objects.add(newHuman);
			ca.add(newHuman);
		}
	}
	
	@Override
	public void update(float dt, BaseObject parent){
		
		commitUpdates();
		ca.update(dt, parent);
		
		//For speed, we get the raw array. We have to be careful to only read		
		Object[] objectArray = objects.getArray();
		int count = objects.getCount();
		
		for(int i = 0; i < count; i++){
			((BaseObject)objectArray[i]).update(dt, this);
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
	public FixedSizeArray<InfectoGameObject> getClose(InfectoGameObject gObject, 
			Vector2 pos, int reach, Team team) {
		int length = objects.getCount();
		Object[] objectArr = objects.getArray();
		
		//TODO pool object arrays?!
		FixedSizeArray<InfectoGameObject> returnObjects = new FixedSizeArray<InfectoGameObject>(length);
		
		for (int i = 0; i < length; i++) {
			InfectoGameObject currentObject = (InfectoGameObject) objectArr[i];
			// If team == Team.All, always proceed
			if(currentObject.team != team) if(team != Team.All) continue;
			
			if(currentObject.pos.distance2(pos) <= reach 
					&& currentObject != gObject){
				returnObjects.add(currentObject);
			}
		}
		
		if(returnObjects.getCount() > 0) return returnObjects;
		return null;
		
	}
	
	/**
	 * Get the closest InfectoGameObject to the position given. The first argument
	 * is the searching gameObject. This is to prevent it returning itself as the 
	 * closest. 
	 * These are quite expensive and scale a lot with complexity, not good!
	 * @param gObject - the searching gameObject. Can be null.
	 * @param pos - get closest gameObject to this position.
	 * @param team - only return gameObjects from the given team. Se Team enum.
	 * @return the closest gameObject to pos, with given team.
	 */
	public InfectoGameObject getClosest(InfectoGameObject gObject,
			Vector2 pos, Team team){
		
		InfectoGameObject closest = null;
		float closestDistance = Float.MAX_VALUE;
		float currentDistance = 0;
		
		final int length = objects.getCount();
		
		Object[] objectArr = objects.getArray();
		
		for (int i = 0; i < length; i++) {
			InfectoGameObject currentObject = (InfectoGameObject) objectArr[i];
			
			// If team == Team.All, always proceed
			if(currentObject.team != team) if(team != Team.All) continue;
			
			currentDistance = currentObject.pos.distance2(pos);
			
			if(currentDistance < closestDistance && objectArr[i] != gObject){
				closestDistance = currentDistance;
				closest = currentObject;
			}
		}
		
		return closest;
	}
	
}
