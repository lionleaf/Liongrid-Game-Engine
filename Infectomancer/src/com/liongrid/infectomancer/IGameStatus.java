package com.liongrid.infectomancer;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LSoundSystem;
import com.liongrid.gameengine.LSoundSystem.Sound;

public class IGameStatus extends LBaseObject{

	public boolean gameStarted = false;
	public int mCoinsGained = 0;
	private IGameObjectHandler gObjectHandler;
	private IGameActivity gameActivity;
	private int mLastHumanCount = -1;

	@Override
	public void update(float dt, LBaseObject parent) {
		gObjectHandler = IGamePointers.gameObjectHandler;
		gameActivity = IGamePointers.curGameActivity;
		
		
		checkGameStarted();
		checkHumansLeft();
		checkAliensLeft();
	}

	private void checkGameStarted() {
		if(gObjectHandler.getCount(ITeam.Alien) > 0) gameStarted = true;
	}

	private void checkAliensLeft() {
		if(gObjectHandler.getCount(ITeam.Alien) <= 0 && gameStarted){
			finishGame();
		}
	}

	private void checkHumansLeft() {
		int humanCount = gObjectHandler.getCount(ITeam.Human);
		if(humanCount < mLastHumanCount){
			int difficulty = IGamePointers.difficulty;
			int deadHumans = mLastHumanCount - humanCount;
			gainCoins(100*difficulty*deadHumans);
		}
		mLastHumanCount = humanCount;

		if(humanCount <= 0 && gameStarted){
			finishGame();
		}
	}

	private void finishGame() {
		IGamePointers.coins += mCoinsGained;
/*
		final CharSequence text = "Round over! Coins gained: "+ mCoinsGained + 
		". Total amount of coins: " + IGameActivity.infectoPointers.coins+".";

		gameActivity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(gameActivity.getApplicationContext(),
						text, Toast.LENGTH_SHORT).show();
				
			}
		});*/
		gameActivity.roundOver(IGamePointers.NumberOfHumans - mLastHumanCount, mCoinsGained);

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	public void gainCoins(int coins){
		mCoinsGained += coins;
	}

}
