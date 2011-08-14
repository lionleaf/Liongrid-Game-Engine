package com.liongrid.infectosaurus;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.widget.ProgressBar;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LButton;
import com.liongrid.gameengine.LGameLoader;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LInput;
import com.liongrid.gameengine.LSurfaceViewPanel;
import com.liongrid.gameengine.LTextureLibrary;
import com.liongrid.gameengine.LView;
import com.liongrid.infectosaurus.crowd.situations.SituationHandler;

public class IGameLoader extends LGameLoader{
	private GameActivity mGameActivity;
	
	public IGameLoader(LSurfaceViewPanel panel, GameActivity gameActivity, 
			Handler handler, ProgressBar progressBar) {
		super(panel, handler, progressBar);
		mGameActivity = gameActivity;
		
	}

	@Override
	protected void init() {
		super.init();
		Log.d("Infectosaurus", "Game Engine loaded");
		InfectoPointers.gameObjectHandler = new InfectoGameObjectHandler();
		postProgress(50);
		InfectoPointers.gameStatus = new GameStatus();
		InfectoPointers.spawnPool = new SpawnPool();
		InfectoPointers.curGameActivity = mGameActivity;
		InfectoPointers.situationHandler = new SituationHandler(10, LBaseObject.gamePointers.map);
		postProgress(70);
		LGamePointers.panel.addToRoot(InfectoPointers.gameObjectHandler);
		LGamePointers.panel.addToRoot(InfectoPointers.gameStatus);
		
		
		
		preLoadTextures();
		postProgress(90);
		spawnMobs();
		postProgress(100);
		startGame();
	}
	
	private void spawnMobs(){
		Bundle extras = InfectoPointers.curGameActivity.getIntent().getExtras();
		//TODO try catch and alert!!!!! on getint
		int difficulty = extras.getInt("com.liongrid.infectosaurus.difficulty");
		int pop = extras.getInt("com.liongrid.infectosaurus.population");
		InfectoPointers.difficulty = difficulty;
		InfectoPointers.NumberOfHumans = pop;
		LGamePointers.map.spawnNPCs(pop,  difficulty);
	}
	
	private void preLoadTextures(){
		LTextureLibrary tLib = LGamePointers.textureLib;
		tLib.allocateTexture(R.drawable.spheremonster01);
		tLib.allocateTexture(R.drawable.mann1);
		tLib.allocateTexture(R.drawable.ants);
	}
	
	private void startGame(){
		LGamePointers.panel.startGame();
		LGamePointers.panel.setRender();
		
		mHandler.post(new Runnable(){
			public void run(){
				InfectoPointers.curGameActivity.onFinishGameLoad();
			}
		});
		
	}
	
	
}
