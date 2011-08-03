package com.liongrid.infectosaurus.crowd.situations;

import android.app.PendingIntent;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.GameObject;
import com.liongrid.gameengine.ObjectHandlerInterface;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.gameengine.tools.Vector2Int;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.map.Map;

/**
 * @author Lastis
 * Contains a threedimensional array of  situations. First and second index is map size x
 * and y in tile sizes. The third index are for multiple situations stored at the same
 * tile position.
 */
public class SituationHandler extends BaseObject 
		implements ObjectHandlerInterface<Situation>{
	
	private Situation[][][] situationsByTiles; // [tileX][tileY][situations]
	// The count of how many situations are stored at tile position x and y. 
	private int[][] situationCnt; 
	private FixedSizeArray<Situation> pendingAdditions;
	private FixedSizeArray<Situation> pendingRemovals;
	// The Vector2Int arrays holds the positions of the pendingRemovals and pendingAdditions
	// situations. The index of pending additions and pending positions should be the same.
	private Vector2Int[] pendingAddPositions;
	private Vector2Int[] pendingRemPositions;
	private Map map;
	
	public SituationHandler(int maxSituations, Map map) {
		this.map = map;
		pendingAdditions = new FixedSizeArray<Situation>(DEFAULT_CAPACITY);
		pendingRemovals = new FixedSizeArray<Situation>(DEFAULT_CAPACITY);
		pendingAddPositions = new Vector2Int[DEFAULT_CAPACITY];
		pendingRemPositions = new Vector2Int[DEFAULT_CAPACITY];
		
		// Init situationsByTiles
		situationCnt = new int[map.size.x][map.size.y];
		situationsByTiles = new Situation[map.size.x][][];
		for(Situation[][] array : situationsByTiles){
			array = new Situation[map.size.y][];
			for(Situation[] situations : array){
				situations = new Situation[maxSituations];
			}
		}
	}
	
	/**
	 * Apply all the situations stored in the handler at the tile position.
	 * @param o
	 */
	public void applySituations(GameObject o){
		Vector2 pos = o.pos;
		int x = (int) pos.x;
		int y = (int) pos.y;
		for(int i = 0; i < situationCnt[x][y]; i++){
			Situation situation = situationsByTiles[x][y][i];
			situation.applySituation(o);
		}
	}

	public void commitUpdates() {
		Object[] array = pendingRemovals.getArray();
		int length = pendingRemovals.getCount();
		for(int i = 0; i < length; i++){
			Vector2Int pos = pendingRemPositions[i];
			int count = situationCnt[pos.x][pos.y];
			situationsByTiles[pos.x][pos.y][count] = (Situation) array[i];
			situationCnt[pos.x][pos.y] ++;
		}
		
		array = pendingAdditions.getArray();
		length = pendingAdditions.getCount();
		for(int i = 0; i < length; i++){
			Vector2Int pos = pendingAddPositions[i];
			removeFromTile((Situation) array[i], pos);
		}
	}
	
	private void removeFromTile(Situation situation, Vector2Int pos) {
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

	public int getCount(Vector2 pos){
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
	
	public void add(Situation o, Vector2Int pos) {
		int index = pendingAdditions.getCount();
		pendingAdditions.add(o);
		pendingAddPositions[index] = pos;
	}

	public void remove(Situation o, Vector2Int pos) {
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
			for(int k = 0; k < situationCnt[i].length; k++){
				situationCnt[i][k] = 0;
			}
		}
	}

	@Override
	public void update(float dt, BaseObject parent) {
		commitUpdates();
	}

	@Override
	public void reset() {
		
	}
	
	public boolean inArray(Situation object, Vector2Int pos){
		Situation[] array = situationsByTiles[pos.x][pos.y];
		for(int i = 0; i < situationCnt[pos.x][pos.y]; i++){
			if(array[i] == object) return true;
		}
		return false;
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
