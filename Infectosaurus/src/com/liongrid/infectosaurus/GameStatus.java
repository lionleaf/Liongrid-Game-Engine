package com.liongrid.infectosaurus;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.CollisionHandler;

public class GameStatus extends BaseObject{

	public boolean gameStarted = false;
	private CollisionHandler<InfectoGameObject> collisionHandler;
	private GameActivity gameActivity;

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
			gameActivity.finish();
		}
	}

	private void checkHumansLeft() {
		if(collisionHandler.getCount(Team.Human.ordinal()) <= 0 && gameStarted){
			gameActivity.finish();
		}
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
