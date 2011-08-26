package com.liongrid.infectomancer.crowd.situations;

import android.util.Log;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LObjectHandlerInterface;
import com.liongrid.gameengine.LMap;
import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.gameengine.tools.LVector2;
import com.liongrid.gameengine.tools.LVector2Int;
import com.liongrid.infectomancer.IGameObject;
import com.liongrid.infectomancer.components.IBehaviorComponent;

/**
 * @author Lastis
 * Contains a three dimensional array of  situations. First and second index is map size x
 * and y in tile sizes. The third index are for multiple situations stored at the same
 * tile position.
 */
public class ISituationHandler extends LBaseObject 
		implements LObjectHandlerInterface<ISituation>{
	
	private ISituation[][][] situationsByTiles; // [tileX][tileY][situations]
	// The count of how many situations are stored at tile position x and y. 
	private int[][] situationCnt; 
	private LFixedSizeArray<ISituation> pendingAdditions;
	private LFixedSizeArray<ISituation> pendingRemovals;
	// The LVector2Int arrays holds the positions of the pendingRemovals and pendingAdditions
	// situations. The index of pending additions and pending positions should be the same.
	private LVector2Int[] pendingAddPositions;
	private LVector2Int[] pendingRemPositions;
	private LMap map;
	
	public ISituationHandler(int maxSituations, LMap map) {
		this.map = map;
		int numberOfTiles = LMap.size.x * LMap.size.y;
		pendingAdditions = new LFixedSizeArray<ISituation>(numberOfTiles/8);
		pendingRemovals = new LFixedSizeArray<ISituation>(numberOfTiles/8);
		pendingAddPositions = new LVector2Int[numberOfTiles/8];
		pendingRemPositions = new LVector2Int[numberOfTiles/8];
		
		// Init situationsByTiles
		int xSize = LMap.size.x;
		int ySize = LMap.size.y;
		
		situationCnt = new int[xSize][ySize];
		
		situationsByTiles = new ISituation[xSize][][];
		for(int i = 0; i < xSize; i++){
			situationsByTiles[i] = new ISituation[ySize][];
			for(int j = 0; j < ySize; j++){
				situationsByTiles[i][j] = new ISituation[maxSituations];
			}
		}
		
		addTestSituations();
	}
	
	private void addTestSituations() {
		int ySize = LMap.size.y;
		int xSize = LMap.size.x;
		ISimpleBehaviorSituation sit = new ISimpleBehaviorSituation();
		for(int x = xSize / 2; x < xSize; x++){
			for(int y = 0; y < ySize; y++){
				addDirect(sit,x,y);
				
			}
		}
		
	}

	/**
	 * This method is designed to update the behavior component to have the same
	 * spatial situations as the situation handler. If the component has a situation
	 * that the handler doesn't have, it will try to remove it. And if the handler has a
	 * situation the component doesn't have, it will try to add it.
	 * @param object - game object
	 * @param component the behavior component.
	 */
	public void updateSituations(IGameObject object, IBehaviorComponent component){
		commitUpdates();
		LVector2 pos = object.mPos;
		int x = (int) pos.x / LMap.TILE_SIZE;
		int y = (int) pos.y / LMap.TILE_SIZE;
		if(x < 0 || x >= situationCnt.length || y < 0 || y >= situationCnt[0].length){
			//If something is outside the map, don`t care about situations
			Log.d("Infectosaurus", "Something went outside the map!");
			return;
		}
		for(int i = 0; i < situationCnt[x][y]; i++){
			ISituation situation = situationsByTiles[x][y][i];
			// If the component does not contain the spatial situation, add the situation
			// to the agent
			if(component.hasSpatialSituation(situation) == false){
				component.addSpatialSituation(situation);
				situation.apply(object, component);
			}
		}
		
		// If the component contains any situations that is not in the situation handler,
		// remove the situation from the component.
		LFixedSizeArray<ISituation> componentSituations = component.getSpatialSituations();
		Object[] rawArray = componentSituations.getArray();
		int length = componentSituations.getCount();
		for(int i = 0; i < length; i++){
			ISituation situation = (ISituation) rawArray[i];
			if(inArray(situation, x, y) == false){
				component.removeSpatialSituation(situation);
				situation.remove(object, component);
			}
		}
	}
	
	public void commitUpdates() {
		Object[] array = pendingRemovals.getArray();
		int length = pendingRemovals.getCount();
		for(int i = 0; i < length; i++){
			removeFromTile((ISituation) array[i], pendingRemPositions[i]);
		}
		
		array = pendingAdditions.getArray();
		length = pendingAdditions.getCount();
		for(int i = 0; i < length; i++){
			addToTile((ISituation) array[i], pendingAddPositions[i]);
		}
	}
	
	private void addToTile(ISituation situation, LVector2Int pos) {
		addToTile(situation, pos.x, pos.y);
	}
	
	private void addToTile(ISituation sit, int x, int y){
		if(inArray(sit, x,y)) return; // If already in array, don't do anything
		int count = situationCnt[x][y];
		situationsByTiles[x][y][count] = sit;
		situationCnt[x][y] ++;
	}

	private void removeFromTile(ISituation situation, LVector2Int pos) {
		ISituation[] array = situationsByTiles[pos.x][pos.y];
		// Remove situation
		for(int i = 0; i < array.length; i++){
			if(array[i] == situation){
				array[i] = null;
				situationCnt[pos.x][pos.y]--;
			}
		}
		// Sort array. Makes sure the array only contains elements from index 0 to
		// situationCnt - 1. (As many situations as situationCnt)
		for(int i = 0; i < situationCnt[pos.x][pos.y]; i++){
			if(array[i] == null){
				for(int j = 0; j < array.length; j++){
					if(array[j] != null){
						array[i] = array[j];
						break;
					}
				}
			}
		}
	}

	public int getCount(LVector2 pos){
		return getCount((int) pos.x, (int) pos.y);
	}
	
	/**
	 * @param x
	 * @param y
	 * @return the count of how many situations are at tile position (x,y).
	 */
	public int getCount(int x, int y){
		return situationCnt[x][y];
	}
	
	public void add(ISituation o, LVector2Int pos) {
		int index = pendingAdditions.getCount();
		pendingAdditions.add(o);
		pendingAddPositions[index] = pos;
	}
	
	public void addDirect(ISituation o, int x, int y){
		addToTile(o, x, y);
	}

	public void remove(ISituation o, LVector2Int pos) {
		int index = pendingRemovals.getCount();
		pendingRemovals.add(o);
		pendingRemPositions[index] = pos;
	}

	/**
	 * Will write all situations stored at tile position (x,y) to given array.
	 * Will write from index 0 and ignores if the array is already full.
	 * @param x
	 * @param y
	 * @param array
	 */
	public void getSituations(int x, int y, ISituation[] array){
		for(int i = 0; i < array.length; i++){
			if(situationsByTiles[x][y][i] == null) break;
			array[i] = situationsByTiles[x][y][i];
		}
	}

	public void clear(){
		for(int i = 0; i < situationsByTiles.length; i++){
			for(int j = 0; j < situationsByTiles[i].length; j++){
				for(int k = 0; k < situationCnt[i][j]; k++){
					situationsByTiles[i][j][k] = null;
				}
			}
		}
		
		for(int i = 0; i < situationCnt.length; i++){
			for(int j = 0; j < situationCnt[i].length; j++){
				situationCnt[i][j] = 0;
			}
		}
	}

	@Override
	public void update(float dt, LBaseObject parent) {
		commitUpdates();
	}

	@Override
	public void reset() {
		
	}
	
	public boolean inArray(Object object, int x, int y){
		ISituation[] array = situationsByTiles[x][y];
		for(int i = 0; i < situationCnt[x][y]; i++){
			if(array[i] == object) return true;
		}
		return false;
	}
	
	public boolean inArray(Object object, LVector2Int pos){
		return inArray(object, pos.x, pos.y);
	}
	
	public boolean inArray(ISituation object)
	throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public void add(ISituation o) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public void remove(ISituation o) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();		
	}

	public int getCount() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}
