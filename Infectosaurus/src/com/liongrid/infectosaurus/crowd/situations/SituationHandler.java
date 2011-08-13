package com.liongrid.infectosaurus.crowd.situations;

import android.app.PendingIntent;
import android.util.Log;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.LObjectHandlerInterface;
import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.gameengine.tools.LVector2;
import com.liongrid.gameengine.tools.LVector2Int;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.components.BehaviorComponent;
import com.liongrid.infectosaurus.map.Map;

/**
 * @author Lastis
 * Contains a three dimensional array of  situations. First and second index is map size x
 * and y in tile sizes. The third index are for multiple situations stored at the same
 * tile position.
 */
public class SituationHandler extends LBaseObject 
		implements LObjectHandlerInterface<Situation>{
	
	private Situation[][][] situationsByTiles; // [tileX][tileY][situations]
	// The count of how many situations are stored at tile position x and y. 
	private int[][] situationCnt; 
	private LFixedSizeArray<Situation> pendingAdditions;
	private LFixedSizeArray<Situation> pendingRemovals;
	// The LVector2Int arrays holds the positions of the pendingRemovals and pendingAdditions
	// situations. The index of pending additions and pending positions should be the same.
	private LVector2Int[] pendingAddPositions;
	private LVector2Int[] pendingRemPositions;
	private Map map;
	
	public SituationHandler(int maxSituations, Map map) {
		this.map = map;
		int numberOfTiles = Map.size.x * Map.size.y;
		pendingAdditions = new LFixedSizeArray<Situation>(numberOfTiles/8);
		pendingRemovals = new LFixedSizeArray<Situation>(numberOfTiles/8);
		pendingAddPositions = new LVector2Int[numberOfTiles/8];
		pendingRemPositions = new LVector2Int[numberOfTiles/8];
		
		// Init situationsByTiles
		int xSize = map.size.x;
		int ySize = map.size.y;
		
		situationCnt = new int[xSize][ySize];
		
		situationsByTiles = new Situation[xSize][][];
		for(int i = 0; i < xSize; i++){
			situationsByTiles[i] = new Situation[ySize][];
			for(int j = 0; j < ySize; j++){
				situationsByTiles[i][j] = new Situation[maxSituations];
			}
		}
		
		addTestSituations();
	}
	
	private void addTestSituations() {
		int ySize = map.size.y;
		int xSize = map.size.x;
		SimpleBehaviorSituation sit = new SimpleBehaviorSituation();
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
	public void updateSituations(InfectoGameObject object, BehaviorComponent component){
		commitUpdates();
		LVector2 pos = object.pos;
		int x = (int) pos.x / map.TILE_SIZE;
		int y = (int) pos.y / map.TILE_SIZE;
		if(x < 0 || x >= situationCnt.length || y < 0 || y >= situationCnt[0].length){
			//If something is outside the map, don`t care about situations
			Log.d("Infectosaurus", "Something went outside the map!");
			return;
		}
		for(int i = 0; i < situationCnt[x][y]; i++){
			Situation situation = situationsByTiles[x][y][i];
			// If the component does not contain the spatial situation, add the situation
			// to the agent
			if(component.hasSpatialSituation(situation) == false){
				component.addSpatialSituation(situation);
				situation.apply(object, component);
			}
		}
		
		// If the component contains any situations that is not in the situation handler,
		// remove the situation from the component.
		LFixedSizeArray<Situation> componentSituations = component.getSpatialSituations();
		Object[] rawArray = componentSituations.getArray();
		int length = componentSituations.getCount();
		for(int i = 0; i < length; i++){
			Situation situation = (Situation) rawArray[i];
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
			removeFromTile((Situation) array[i], pendingRemPositions[i]);
		}
		
		array = pendingAdditions.getArray();
		length = pendingAdditions.getCount();
		for(int i = 0; i < length; i++){
			addToTile((Situation) array[i], pendingAddPositions[i]);
		}
	}
	
	private void addToTile(Situation situation, LVector2Int pos) {
		addToTile(situation, pos.x, pos.y);
	}
	
	private void addToTile(Situation sit, int x, int y){
		if(inArray(sit, x,y)) return; // If already in array, don't do anything
		int count = situationCnt[x][y];
		situationsByTiles[x][y][count] = sit;
		situationCnt[x][y] ++;
	}

	private void removeFromTile(Situation situation, LVector2Int pos) {
		Situation[] array = situationsByTiles[pos.x][pos.y];
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
	
	public void add(Situation o, LVector2Int pos) {
		int index = pendingAdditions.getCount();
		pendingAdditions.add(o);
		pendingAddPositions[index] = pos;
	}
	
	public void addDirect(Situation o, int x, int y){
		addToTile(o, x, y);
	}

	public void remove(Situation o, LVector2Int pos) {
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
	public void getSituations(int x, int y, Situation[] array){
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
		Situation[] array = situationsByTiles[x][y];
		for(int i = 0; i < situationCnt[x][y]; i++){
			if(array[i] == object) return true;
		}
		return false;
	}
	
	public boolean inArray(Object object, LVector2Int pos){
		return inArray(object, pos.x, pos.y);
	}
	
	public boolean inArray(Situation object)
	throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public void add(Situation o) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	public void remove(Situation o) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();		
	}

	public int getCount() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
}
