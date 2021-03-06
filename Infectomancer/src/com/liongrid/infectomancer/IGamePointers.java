package com.liongrid.infectomancer;

import android.view.View;

import com.liongrid.gameengine.LMusic;
import com.liongrid.gameengine.tmx.TMXTiledMap;
import com.liongrid.infectomancer.crowd.situations.ISituationHandler;

public class IGamePointers {
	public static Infectosaurus currentSaurus = null;

	//Persistent variables:
	public static int coins = 0; 
	
	private static int mXP = 0;
	private static int mLevel = 0;
	
	//Per game variables:
	public static int NumberOfHumans = 15;
	public static IGameObjectHandler gameObjectHandler;
	public static int difficulty = -1;
	public static IGameStatus gameStatus;
	public static IGameActivity curGameActivity;
	public static ISpawnPool spawnPool;
	public static ISituationHandler situationHandler;
	public static LMusic music;

	public static View hudView;

	public static TMXTiledMap TMXMap;
	
	
	public static void resetGameVars() {
		NumberOfHumans = -1;
		gameObjectHandler = null;
		difficulty = -1;
		gameStatus = null;
		curGameActivity = null;
		coins = 0;
		spawnPool = null;
		situationHandler = null;
		currentSaurus = null;
		music = null;
		
	}
	
	public static int getLevel(){
		return mLevel;
	}
	
	
}
