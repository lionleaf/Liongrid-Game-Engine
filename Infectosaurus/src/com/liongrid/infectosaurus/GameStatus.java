package com.liongrid.infectosaurus;

import android.util.Log;
import android.widget.Toast;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCollisionHandlerMultipleArrays;

public class GameStatus extends LBaseObject{

	public boolean gameStarted = false;
	public int mCoinsGained = 0;
	private InfectoGameObjectHandler gObjectHandler;
	private GameActivity gameActivity;
	private int mLastHumanCount = -1;


	@Override
	public void update(float dt, LBaseObject parent) {
		gObjectHandler = GameActivity.infectoPointers.gameObjectHandler;
		gameActivity = GameActivity.infectoPointers.curGameActivity;

		checkGameStarted();
		checkHumansLeft();
		checkAliensLeft();
	}

	private void checkGameStarted() {
		if(gObjectHandler.getCount(Team.Alien) > 0) gameStarted = true;
	}

	private void checkAliensLeft() {
		if(gObjectHandler.getCount(Team.Alien) <= 0 && gameStarted){
			finishGame();
		}
	}

	private void checkHumansLeft() {
		int humanCount = gObjectHandler.getCount(Team.Human);
		if(humanCount < mLastHumanCount){
			int difficulty = GameActivity.infectoPointers.difficulty;
			int deadHumans = mLastHumanCount - humanCount;
			gainCoins(100*difficulty*deadHumans);
		}
		mLastHumanCount = humanCount;

		if(humanCount <= 0 && gameStarted){
			finishGame();
		}
	}

	private void finishGame() {
		GameActivity.infectoPointers.coins += mCoinsGained;
/*
		final CharSequence text = "Round over! Coins gained: "+ mCoinsGained + 
		". Total amount of coins: " + GameActivity.infectoPointers.coins+".";

		gameActivity.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(gameActivity.getApplicationContext(),
						text, Toast.LENGTH_SHORT).show();
				
			}
		});*/
		gameActivity.roundOver(InfectoPointers.NumberOfHumans - mLastHumanCount, mCoinsGained);

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	public void gainCoins(int coins){
		mCoinsGained += coins;
	}

}
