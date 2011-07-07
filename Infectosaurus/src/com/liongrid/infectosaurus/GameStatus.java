package com.liongrid.infectosaurus;

import android.widget.Toast;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.CollisionHandler;

public class GameStatus extends BaseObject{

	public boolean gameStarted = false;
	public int mCoinsGained = 0;
	private CollisionHandler<InfectoGameObject> collisionHandler;
	private GameActivity gameActivity;
	private int mLastHumanCount = -1;


	@Override
	public void update(float dt, BaseObject parent) {
		collisionHandler = GameActivity.infectoPointers.gameObjectHandler.mCH;
		gameActivity = GameActivity.infectoPointers.curGameActivity;

		checkGameStarted();
		checkHumansLeft();
		checkAliensLeft();
	}

	private void checkGameStarted() {
		if(collisionHandler.getCount(Team.Alien.ordinal()) > 0) gameStarted = true;
	}

	private void checkAliensLeft() {
		if(collisionHandler.getCount(Team.Alien.ordinal()) <= 0 && gameStarted){
			finishGame();
		}
	}

	private void checkHumansLeft() {
		int humanCount = collisionHandler.getCount(Team.Human.ordinal());
		if(humanCount < mLastHumanCount){
			int difficulty = GameActivity.infectoPointers.difficulty;
			int deadHumans = mLastHumanCount - humanCount;
			gainCoins(100*difficulty*deadHumans);
		}
		mLastHumanCount = humanCount;

		if(humanCount <= 0){
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
		gameActivity.roundOver();

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	public void gainCoins(int coins){
		mCoinsGained += coins;
	}

}
