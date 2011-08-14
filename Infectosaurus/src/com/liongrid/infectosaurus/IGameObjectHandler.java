package com.liongrid.infectosaurus;

import android.util.Log;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCamera;
import com.liongrid.gameengine.LCollisionHandler;
import com.liongrid.gameengine.LCollisionObject;
import com.liongrid.gameengine.LObjectHandler;
import com.liongrid.gameengine.LMap;
import com.liongrid.gameengine.tools.LVector2;

/**
 * @author Lastis
 * This class needs to hold all the GameObjects and sort the 
 * components in a way that they can be done in the right order 
 */
public class IGameObjectHandler extends LObjectHandler<IGameObject> {
	public static int UNITS_PER_COLLISION_AREA_X;
	public static int UNITS_PER_COLLISION_AREA_Y;
	private static final int DEFAULT_CAPACITY = 256;
	
	private LCollisionHandler[][] mCollisionAreas;
	public int mCollisionAreasLengthX;
	public int mCollisionAreasLengthY;
	
	public IGameObjectHandler(){
		super(DEFAULT_CAPACITY);
		UNITS_PER_COLLISION_AREA_X = LCamera.unit * 8;
		UNITS_PER_COLLISION_AREA_Y = LCamera.unit * 8;
		mCollisionAreasLengthX = LMap.sizePx.x / UNITS_PER_COLLISION_AREA_X + 1;
		mCollisionAreasLengthY = LMap.sizePx.y / UNITS_PER_COLLISION_AREA_Y + 1;
		mCollisionAreas = new LCollisionHandler[mCollisionAreasLengthX][];
		for(int i = 0; i < mCollisionAreasLengthX; i++){
			mCollisionAreas[i] = new LCollisionHandler[mCollisionAreasLengthY];
			for(int j = 0; j < mCollisionAreasLengthY; j++){
				mCollisionAreas[i][j] = new LCollisionHandler(DEFAULT_CAPACITY);
			}
		}
	}
	
	@Override
	public void add(IGameObject object) {
		super.add(object);
	}
	
	@Override
	public void remove(IGameObject infectoGameObject) {
		super.remove(infectoGameObject);
	}
	
	@Override
	public void update(float dt, LBaseObject parent){
		
		commitUpdates();
		//For speed, we get the raw array. We have to be careful to only read		
		Object[] objectArray = objects.getArray();
		int count = objects.getCount();
		for(int i = 0; i < count; i++){
			((LBaseObject)objectArray[i]).update(dt, this);
		}
		
		refreshCollisionAreas(count, objectArray);
		updateCollisionAreas(count, objectArray, parent, dt);
	}

	private void refreshCollisionAreas(int count, Object[] objectArray) {
		//Clear
		for(int i = 0; i < mCollisionAreas.length; i++){
			for(int j = 0; j < mCollisionAreas[i].length; j++){
				mCollisionAreas[i][j].clear();
			}
		}
		
		//Add
		for(int i = 0; i < count; i++){
			IGameObject infectoObject = (IGameObject)objectArray[i];
			if(infectoObject.collisionObject == null) return;
			addToCorrectCollisionHandler((IGameObject)objectArray[i]);
		}
	}
	
	private void updateCollisionAreas(int count, Object[] objectArray,
			LBaseObject parent, float dt) {
		for(int i = 0; i < mCollisionAreas.length; i++){
			for(int j = 0; j < mCollisionAreas[i].length; j++){
				mCollisionAreas[i][j].update(dt, parent);
			}
		}
	}

	public void addToCorrectCollisionHandler(IGameObject gameObject) {
		LCollisionObject collisionObject = gameObject.collisionObject;
		if(collisionObject == null) return;
		LVector2 pos = gameObject.pos;
		float halfWidth = (float) (gameObject.mWidth/2.0);
		float halfHeight = (float) (gameObject.mHeigth/2.0);
		
		int minX = (int) ((pos.x - halfWidth)/UNITS_PER_COLLISION_AREA_X);
		int maxX = (int) ((pos.x + halfWidth)/UNITS_PER_COLLISION_AREA_X);
		
		int minY = (int) ((pos.y - halfHeight)/UNITS_PER_COLLISION_AREA_Y);
		int maxY = (int) ((pos.y - halfHeight)/UNITS_PER_COLLISION_AREA_Y);
		if(minX < 0 || maxX >= mCollisionAreasLengthX ||
		   minY < 0 || maxY >= mCollisionAreasLengthY){
			Log.d(IMainMenuActivity.TAG, "A infectoGameObject seems to be moving outside the map");
			return;
		}
		for(int i = minX; i <= maxX; i++){
			for(int j = minY; j <= maxY; j++){
				if(mCollisionAreas[i][j] == null) continue;
				mCollisionAreas[i][j].add(collisionObject);
			}
		}
	}
	
	public int getCount(ITeam team){
		int count = 0;
		int length = objects.getCount();
		Object[] array = objects.getArray();
		for(int i = 0; i < length; i++){
			IGameObject gObject = (IGameObject) array[i];
			if(gObject.team == team) count += 1;
		}
		return count;
	}
	
	public IGameObject getClosest(LVector2 pos, ITeam team, IGameObject self){
		int i; float closestDistance = Float.MAX_VALUE;
		int length = objects.getCount();
		Object[] array = objects.getArray();
		IGameObject closest = null;
		IGameObject curObject = null;
		for(i = 0; i < length; i++){
			curObject = (IGameObject) array[i];
			if(curObject.team != team || curObject == self) continue;
			float distance = pos.distance2(curObject.pos);
			if(distance < closestDistance) {
				closestDistance = distance;
				closest = curObject;
			}
		}
		return closest;
	} 
	

	public IGameObject[] getClose(LVector2 pos, float within, 
			ITeam team, IGameObject[] array, IGameObject self){
		int count = 0; float dis2;
		int length = objects.getCount();
		Object[] gArray = objects.getArray();
		for(int i = 0; i < length; i++){
			IGameObject gObject = (IGameObject) gArray[i];
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
