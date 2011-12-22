package com.liongrid.infectomancer;

import android.util.Log;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCamera;
import com.liongrid.gameengine.LCollisionHandler;
import com.liongrid.gameengine.LCollisionObject;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LObjectHandler;
import com.liongrid.gameengine.LMap;
import com.liongrid.gameengine.tools.LVector2;

/**
 * @author Lastis
 * This class needs to hold all the GameObjects and sort the 
 * components in a way that they can be done in the right order 
 */
public class IGameObjectHandler extends LObjectHandler<IGameObject> {
	private static final int DEFAULT_CAPACITY = 512;
	private LCollisionHandler mCollisonHandler = new LCollisionHandler(DEFAULT_CAPACITY);

	public IGameObjectHandler(){
		super(DEFAULT_CAPACITY);
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
		refreshCollisionArea(count, objectArray);
		updateCollisionArea(dt, parent);
	}

	private void updateCollisionArea(float dt, LBaseObject parent) {
		mCollisonHandler.update(dt, parent);
	}

	private void refreshCollisionArea(int count, Object[] objectArray) {
		mCollisonHandler.clear();
		for(int i = 0; i < count; i++){
			IGameObject infectoObject = (IGameObject)objectArray[i];
			if(infectoObject.collisionObject == null) return;
			mCollisonHandler.add(infectoObject.collisionObject);
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

	public IGameObject getClosestOld(LVector2 pos, ITeam team, LGameObject parent){
		int i; float closestDistance = Float.MAX_VALUE;
		int length = objects.getCount();
		Object[] array = objects.getArray();
		IGameObject closest = null;
		IGameObject curObject = null;
		for(i = 0; i < length; i++){
			curObject = (IGameObject) array[i];
			if(curObject.team != team || curObject == parent) continue;
			float distance = pos.distance2(curObject.pos);
			if(distance < closestDistance) {
				closestDistance = distance;
				closest = curObject;
			}
		}
		return closest;
	}

//	/**
//	 * Get`s the closest gameobject by searching radially outwards. Still experimental. 
//	 * Assumes that collision zones are square.
//	 * 
//	 * @param pos - the position you are searching from.
//	 * @param team - the team that you are looking for.
//	 * @param self - the pointer to the searching object, to make sure it 
//	 * 			wont simply return itself
//	 * @param experimental - value does not matter, 
//	 * 			but any value will make sure you use the experimental overload.
//	 * @return the gameobject closest to pos.
//	 */
//	public LGameObject getClosest(LVector2 pos, ITeam team, LGameObject self, boolean experimental){
//		int r; float closestDistance2 = Float.MAX_VALUE;
//		LGameObject closest = null;
//		// Search with bigger and bigger radius until we find something:
//		//TODO FIX THIS LOOP! IT GOES EMPTY for A bit too long!
//		for(r = 1; closest  == null && r < mCollisionAreasLengthX+mCollisionAreasLengthY ; r++){
//			for(int y = -r; y <= r; y++){
//				int minX = (int) Math.ceil(radialSearchFunc(r-1,y));
//				int maxX = (int) Math.floor(radialSearchFunc(r,y));
//				LGameObject tempClosest = getClosestInXRange(minX, maxX, y, pos, team);
//				if(tempClosest == null) continue;
//				float tempDist2 = tempClosest.pos.distance2(pos);
//				if(tempDist2 < closestDistance2){
//					closest = tempClosest;
//					closestDistance2 = tempDist2;
//				}
//				// TODO: Make sure it stops searching when every zone has been checked.
//			}
//		}
//
//		//Something could still be closer, since the circles is not perfect. Check for closer 
//		//objects by scanning the shell of handlers between last scan and r = dist
//		if(closest == null) return null;
//		
//		float distInZones = (float) (Math.sqrt(closestDistance2) / 
//				(LGamePointers.map.getWidth()/mCollisionAreasLengthX));
//
//		for(int y = -r; y <= r; y++){
//			int minX = (int) Math.ceil(radialSearchFunc(r,y));
//			int maxX = (int) Math.floor(radialSearchFunc(distInZones,y)); 
//			IGameObject tempClosest = getClosestInXRange(minX, maxX, y, pos, team);
//
//			if(tempClosest != null && tempClosest.team == team){
//				float tempDist2 = tempClosest.pos.distance2(pos);
//				if(tempDist2 < closestDistance2){
//					closest = tempClosest;
//					closestDistance2 = tempDist2;
//				}
//			}
//		}
//
//		return closest;
//	}


//	/**
//	 * Get`s the closest object to pos in the range specified by min and max x of collisionHandlers
//	 * @param minX - The minimum x coordinate of collisionHandlers to search in. Inclusive.
//	 * @param maxX - The maximum x coordinate of collisionHandlers to search in. Inclusive.
//	 * @param y - The fixed y coordinate of the collisionHandlers to search in.
//	 * @param pos - The coordinate of the point you want to find the closest object to
//	 * @return The closest object within the specified collisionhandlers.
//	 */
//	private IGameObject getClosestInXRange(int minX, int maxX, int y, LVector2 pos, ITeam team) {
//		//Distance is squared, to save computing power
//		double closestDist2 = Double.MAX_VALUE;
//		IGameObject closest = null;
//		IGameObject tempClosest = null;
//		double tempDist2;
//
//		for(int x = minX; x <= maxX; x++){
//			int resX = (int) (x + pos.x / 
//					(LGamePointers.map.getWidth()/mCollisionAreasLengthX));
//			int resY = (int) (y + pos.y / 
//					(LGamePointers.map.getWidth()/mCollisionAreasLengthX));
//
//			if(!(resX < 0 || resY < 0 || resX >= mCollisionAreasLengthX 
//					|| resY >= mCollisionAreasLengthY)) {
//				//TODO Make it LGameObject, and more generic?
//				LCollisionObject closestColOb = mCollisionAreas[resX][resY].getClosest(pos, team.ordinal());
//				if(closestColOb == null) continue;
//				tempClosest = (IGameObject) closestColOb.owner;
//				if(tempClosest != null && tempClosest.team == team){
//					
//					tempDist2 = tempClosest.pos.distance2(pos);
//					if(tempDist2 < closestDist2){
//						closest = tempClosest;
//						closestDist2 = tempDist2; 
//					}
//				}
//
//			}
//
//			resX = (int) (pos.x - x / 
//					(LGamePointers.map.getWidth()/mCollisionAreasLengthX));
//			if(!(resX < 0 || resY < 0 || resX >= mCollisionAreasLengthX 
//					|| resY >= mCollisionAreasLengthY) ){
//
//				tempClosest = (IGameObject) mCollisionAreas[resX][resY].getClosest(pos, team.ordinal()).owner;
//				if(tempClosest != null && tempClosest.team == team){
//					tempDist2 = tempClosest.pos.distance2(pos);
//					if(tempDist2 < closestDist2){
//						closest = tempClosest;
//						closestDist2 = tempDist2; 
//					}
//				}
//			}
//
//		}
//		return closest;
//	}
//
//	private double radialSearchFunc(double a, double b){
//		double nr = a*a-b*b;
//		if(nr <= 0) return 0;
//		double answer =Math.sqrt(a*a-b*b);
//		return answer;
//	}

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
