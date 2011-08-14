package com.liongrid.infectosaurus;

import com.liongrid.infectosaurus.crowd.situations.ISituationHandler;

public class IGamePointers {
	public static int NumberOfHumans = 15;
	public static IGameObjectHandler gameObjectHandler;
	public static int difficulty;
	public static IGameStatus gameStatus;
	public static IGameActivity curGameActivity;
	public static int coins = 0; 
	public static ISpawnPool spawnPool;
	public static ISituationHandler situationHandler;
}
