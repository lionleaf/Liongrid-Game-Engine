package com.liongrid.infectosaurus;

import com.liongrid.infectosaurus.crowd.situations.SituationHandler;

public class InfectoPointers {
	public static int NumberOfHumans = 15;
	public InfectoGameObjectHandler gameObjectHandler;
	public HUDObjectHandler HUDObjectHandler;
	public int difficulty;
	public GameStatus gameStatus;
	public GameActivity curGameActivity;
	public static int coins = 0; 
	public SpawnPool spawnPool;
	public SituationHandler situationHandler;
}
