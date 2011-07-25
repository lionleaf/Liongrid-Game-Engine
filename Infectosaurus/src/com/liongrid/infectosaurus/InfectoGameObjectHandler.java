package com.liongrid.infectosaurus;

import java.util.Random;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Camera;
import com.liongrid.gameengine.CollisionHandler;
import com.liongrid.gameengine.ObjectHandler;
import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.map.Map;

/**
 * @author Lastis
 * This class needs to hold all the GameObjects and sort the 
 * components in a way that they can be done in the right order 
 */
public class InfectoGameObjectHandler extends ObjectHandler<InfectoGameObject> {
	public static final int UNITS_PER_COLLISION_AREA_X = 8;
	public static final int UNITS_PER_COLLISION_AREA_Y = 8;
	private static final int DEFAULT_CAPACITY = 256;
	
	public CollisionHandler mCollisionHandler;
	private CollisionHandler[][] mCollisionAreas;
	
	public InfectoGameObjectHandler(){
		super(DEFAULT_CAPACITY);
		mCollisionHandler = new CollisionHandler(Team.values().length, DEFAULT_CAPACITY);
		
//		int areasX = (int) Math.ceil(Map.mapSizePx.x / UNITS_PER_COLLISION_AREA_X);
//		int areasY = (int) Math.ceil(Map.mapSizePx.y / UNITS_PER_COLLISION_AREA_Y);
//		mCollisionAreas = new CollisionHandler[areasX][areasY];
//		for(int i = 0; i < areasX; i++){
//			for(int j = 0; j < areasY; j++){
//				mCollisionAreas[i][j] = 
//					new CollisionHandler(Team.values().length, DEFAULT_CAPACITY);
//			}
//		}
	}
	
	@Override
	public void add(InfectoGameObject object) {
		super.add(object);
		if(object.collisionObject != null){
			mCollisionHandler.add(object.collisionObject);
		}
	}
	
	@Override
	public void remove(InfectoGameObject infectoGameObject) {
		super.remove(infectoGameObject);
	}
	
	@Override
	public void update(float dt, BaseObject parent){
		
		commitUpdates();
		//For speed, we get the raw array. We have to be careful to only read		
		Object[] objectArray = objects.getArray();
		int count = objects.getCount();
		
		for(int i = 0; i < count; i++){
			((BaseObject)objectArray[i]).update(dt, this);
		}
		
		updateCollisionAreas(count, objectArray);
		mCollisionHandler.update(dt, parent);
	}

	/**
	 * Clears the collisionHandler and adds them again.
	 * @param count
	 * @param objectArray
	 */
	private void updateCollisionAreas(int count, Object[] objectArray) {
		mCollisionHandler.clear();
		for(int i = 0; i < count; i++){
			InfectoGameObject infectoObject = (InfectoGameObject)objectArray[i]; 
			mCollisionHandler.add(infectoObject.collisionObject);
			
//			moveToCorrectCollisionHandler((InfectoGameObject)objectArray[i]);
		}
	}

	private void moveToCorrectCollisionHandler(InfectoGameObject gameObject) {
		Vector2 pos = gameObject.pos;
		int minX = (int) Math.ceil(pos.x/UNITS_PER_COLLISION_AREA_X);
		int maxX = (int) Math.ceil(pos.x/UNITS_PER_COLLISION_AREA_X);
		
		int minY = (int) Math.ceil(pos.y/UNITS_PER_COLLISION_AREA_Y);
		int maxY = (int) Math.ceil(pos.y/UNITS_PER_COLLISION_AREA_Y);
		
	}

//	/**
//	 * Finds all gameobjects within mReach of gObject
//	 * 
//	 * @param gObject - The object who wants something close
//	 * @param mReach - How far you want to search
//	 * @return - A FixedSizeArray with the results. (Remember to release it!) 
//	 * Or null if there is nothing within mReach. It will not return itself.
//	 */ 
//	public FixedSizeArray<InfectoGameObject> getClose(InfectoGameObject gObject, 
//			Vector2 pos, int reach, Team team) {
//		int length = objects.getCount();
//		Object[] objectArr = objects.getArray();
//		
//		//TODO pool object arrays?!
//		FixedSizeArray<InfectoGameObject> returnObjects = new FixedSizeArray<InfectoGameObject>(length);
//		
//		for (int i = 0; i < length; i++) {
//			InfectoGameObject currentObject = (InfectoGameObject) objectArr[i];
//			// If team == Team.All, always proceed
//			if(currentObject.team != team) if(team != Team.All) continue;
//			
//			if(currentObject.pos.distance2(pos) <= reach 
//					&& currentObject != gObject){
//				returnObjects.add(currentObject);
//			}
//		}
//		
//		if(returnObjects.getCount() > 0) return returnObjects;
//		return null;
//		
//	}
//	
//	/**
//	 * Get the closest InfectoGameObject to the position given. The first argument
//	 * is the searching gameObject. This is to prevent it returning itself as the 
//	 * closest. 
//	 * These are quite expensive and scale a lot with complexity, not good!
//	 * @param gObject - the searching gameObject. Can be null.
//	 * @param pos - get closest gameObject to this position.
//	 * @param team - only return gameObjects from the given team. Se Team enum.
//	 * @return the closest gameObject to pos, with given team.
//	 */
//	public InfectoGameObject getClosest(InfectoGameObject gObject,
//			Vector2 pos, Team team){
//		
//		InfectoGameObject closest = null;
//		float closestDistance = Float.MAX_VALUE;
//		float currentDistance = 0;
//		
//		final int length = objects.getCount();
//		
//		Object[] objectArr = objects.getArray();
//		
//		for (int i = 0; i < length; i++) {
//			InfectoGameObject currentObject = (InfectoGameObject) objectArr[i];
//			
//			// If team == Team.All, always proceed
//			if(currentObject.team != team) if(team != Team.All) continue;
//			
//			currentDistance = currentObject.pos.distance2(pos);
//			
//			if(currentDistance < closestDistance && objectArr[i] != gObject){
//				closestDistance = currentDistance;
//				closest = currentObject;
//			}
//		}
//		return closest;
//	}
//	
}
