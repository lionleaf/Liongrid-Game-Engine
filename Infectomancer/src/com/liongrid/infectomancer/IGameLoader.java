package com.liongrid.infectomancer;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import com.liongrid.gameengine.LGameLoader;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LSurfaceViewPanel;
import com.liongrid.gameengine.LTextureLibrary;
import com.liongrid.infectomancer.crowd.situations.ISituationHandler;
import com.liongrid.infectomancer.R;

public class IGameLoader extends LGameLoader{
	private IGameActivity mGameActivity;
	
	public IGameLoader(LSurfaceViewPanel panel, IGameActivity gameActivity, 
			Handler handler, ProgressBar progressBar) {
		super(panel,gameActivity, handler, progressBar);
		mGameActivity = gameActivity;
		
	}


	
	@Override
	protected void instantiateGameClasses() {
		IGamePointers.resetGameVars();
		IGamePointers.gameObjectHandler = new IGameObjectHandler();
		postProgress(50);
		IGamePointers.gameStatus = new IGameStatus();
		IGamePointers.spawnPool = new ISpawnPool();
		IGamePointers.curGameActivity = mGameActivity;
		IGamePointers.situationHandler = new ISituationHandler(10, LGamePointers.map);
		postProgress(70);
		LGamePointers.panel.addToRoot(IGamePointers.gameObjectHandler);
		LGamePointers.panel.addToRoot(IGamePointers.gameStatus);
	}

	protected void preLoadTextures(){
		LTextureLibrary tLib = LGamePointers.textureLib;
		tLib.allocateTexture(R.drawable.squaremonster);
		tLib.allocateTexture(R.drawable.spheremonster01);
		tLib.allocateTexture(R.drawable.mann1);
		tLib.allocateTexture(R.drawable.reaper);
		postProgress(80);
	}
	
	protected void setupGame(){
		Bundle extras = IGamePointers.curGameActivity.getIntent().getExtras();
		//TODO try catch and alert!!!!! on getint
		int difficulty = extras.getInt("com.liongrid.infectomancer.difficulty");
		int pop = extras.getInt("com.liongrid.infectomancer.population");
		IGamePointers.difficulty = difficulty;
		IGamePointers.NumberOfHumans = pop;
		LGamePointers.map.spawnNPCs(pop,  difficulty);
		postProgress(100);
	}
	
}
