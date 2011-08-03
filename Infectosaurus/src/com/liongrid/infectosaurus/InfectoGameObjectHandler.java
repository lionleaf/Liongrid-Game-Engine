package com.liongrid.infectosaurus;

import java.util.Random;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Camera;
import com.liongrid.gameengine.CollisionHandler;
import com.liongrid.gameengine.CollisionHandlerMultipleArrays;
import com.liongrid.gameengine.CollisionObject;
import com.liongrid.gameengine.ObjectHandler;
import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.map.Map;

/**
 * @author Lastis
 * This class needs to hold all the GameObjects and sort the 
 * components in a way that they can be done in the right order 
 */
public class InfectoGameObjectHandler extends ObjectHandler<InfectoGameObject> {
	public static int UNITS_PER_COLLISION_AREA_X;
	public static int UNITS_PER_COLLISION_AREA_Y;
	private static final int DEFAULT_CAPACITY = 256;
	
//	public CollisionHandler mCollisionHandler;
	private CollisionHandler[][] mCollisionAreas;
	public int mCollisionAreasLengthX;
	public int mCollisionAreasLengthY;
	
	public InfectoGameObjectHandler(){
		super(DEFAULT_CAPACITY);
//		mCollisionHandler = new CollisionHandler(Team.values().length, DEFAULT_CAPACITY);
		UNITS_PER_COLLISION_AREA_X = Camera.unit * 8;
		UNITS_PER_COLLISION_AREA_Y = Camera.unit * 8;
		mCollisionAreasLengthX = Map.sizePx.x / UNITS_PER_COLLISION_AREA_X + 1;
		mCollisionAreasLengthY = Map.sizePx.y / UNITS_PER_COLLISION_AREA_Y + 1;
		mCollisionAreas = new CollisionHandler[mCollisionAreasLengthX][];
		for(int i = 0; i < mCollisionAreasLengthX; i++){
			mCollisionAreas[i] = new CollisionHandler[mCollisionAreasLengthY];
			for(int j = 0; j < mCollisionAreasLengthY; j++){
				mCollisionAreas[i][j] = new CollisionHandler(DEFAULT_CAPACITY);
			}
		}
	}
	
	@Override
	public void add(InfectoGameObject object) {
		super.add(object);
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
		
		refreshCollisionAreas(count, objectArray);
		updateCollisionAreas(count, objectArray, parent, dt);
	}

	private void refreshCollisionAreas(int count, Object[] objectArray) {
		//Clear
//		mCollisionHandler.clear();
		for(int i = 0; i < mCollisionAreas.length; i++){
			for(int j = 0; j < mCollisionAreas[i].length; j++){
				mCollisionAreas[i][j].clear();
			}
		}
		
		//Add
		for(int i = 0; i < count; i++){
			InfectoGameObject infectoObject = (InfectoGameObject)objectArray[i];
			if(infectoObject.collisionObject == null) return;
//			mCollisionHandler.add(infectoObject.collisionObject);
			addToCorrectCollisionHandler((InfectoGameObject)objectArray[i]);
		}
	}
	
	private void updateCollisionAreas(int count, Object[] objectArray,
			BaseObject parent, float dt) {
		for(int i = 0; i < mCollisionAreas.length; i++){
			for(int j = 0; j < mCollisionAreas[i].length; j++){
				mCollisionAreas[i][j].update(dt, parent);
			}
		}
	}

	private void addToCorrectCollisionHandler(InfectoGameObject gameObject) {
		CollisionObject collisionObject = gameObject.collisionObject;
		if(collisionObject == null) return;
		Vector2 pos = gameObject.pos;
		float halfWidth = (float) (gameObject.mWidth/2.0);
		float halfHeight = (float) (gameObject.mHeigth/2.0);
		
		int minX = (int) ((pos.x - halfWidth)/UNITS_PER_COLLISION_AREA_X);
		int maxX = (int) ((pos.x + halfWidth)/UNITS_PER_COLLISION_AREA_X);
		
		int minY = (int) ((pos.y - halfHeight)/UNITS_PER_COLLISION_AREA_Y);
		int maxY = (int) ((pos.y - halfHeight)/UNITS_PER_COLLISION_AREA_Y);
		if(minX < 0 || maxX >= mCollisionAreasLengthX ||
		   minY < 0 || maxY >= mCollisionAreasLengthY){
			Log.d(Main.TAG, "A infectoGameObject seems to be moving outside the map");
			return;
		}
		for(int i = minX; i <= maxX; i++){
			for(int j = minY; j <= maxY; j++){
				if(mCollisionAreas[i][j] == null) continue;
				mCollisionAreas[i][j].add(collisionObject);
			}
		}
	}
	
	public int getCount(Team team){
		int count = 0;
		int length = objects.getCount();
		Object[] array = objects.getArray();
		for(int i = 0; i < length; i++){
			InfectoGameObject gObject = (InfectoGameObject) array[i];
			if(gObject.team == team) count += 1;
		}
		return count;
	}
	
	public InfectoGameObject getClosest(Vector2 pos, Team team, InfectoGameObject self){
		int i; float closestDistance = Float.MAX_VALUE;
		int length = objects.getCount();
		Object[] array = objects.getArray();
		InfectoGameObject closest = null;
		InfectoGameObject curObject = null;
		for(i = 0; i < length; i++){
			curObject = (InfectoGameObject) array[i];
			if(curObject.team != team || curObject == self) continue;
			float distance = pos.distance2(curObject.pos);
			if(distance < closestDistance) {
				closestDistance = distance;
				closest = curObject;
			}
		}
		return closest;
	} 
	

	public InfectoGameObject[] getClose(Vector2 pos, float within, 
			Team team, InfectoGameObject[] array, InfectoGameObject self){
		int count = 0; float dis2;
		int length = objects.getCount();
		Object[] gArray = objects.getArray();
		for(int i = 0; i < length; i++){
			InfectoGameObject gObject = (InfectoGameObject) gArray[i];
			if(gObject.team != team || gObject == self) continue;
			if(count >= array.length) return array;
			dis2 = pos.distance2(gObject.pos);
			if(dis2 < within * within){
				array[count] = gObject;
				count ++;
			}
		}
		return array;
	}
}
