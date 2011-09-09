package com.liongrid.thumbfighter;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LGamePointers;

public class TGameStatus extends LBaseObject{
	private float scoreBalance = 0;
	private LDrawableBitmap bitmap;
	private int mWidth;
	private float mHeight;
	
	public TGameStatus() {
		tryMakeDrawable(); 
	}
	
	private void tryMakeDrawable(){
		if(LGamePointers.panel == null) return;
		mWidth = LGamePointers.panel.getWidth();
		mHeight = LGamePointers.panel.getHeight();
		if(mWidth <= 0 || mHeight <= 0) return;
		bitmap = new LDrawableBitmap(
				LGamePointers.textureLib.allocateTexture(R.drawable.green),
				mWidth,20);
	}
	
	@Override
	public void update(float dt, LBaseObject parent) {
		if(bitmap == null) tryMakeDrawable();
		if(bitmap == null) return;
		float y = (mHeight/2f) + scoreBalance;
		if(LGamePointers.renderSystem == null) return;
		
		LGamePointers.renderSystem.scheduleForDraw(bitmap,1, y ,true);
	}
	
	public void addScore(float score, TPlayerID player){
		scoreBalance += player == TPlayerID.player2 ? score : -score;
	}
	
	public void explosion(float y){
		scoreBalance += (y-(mHeight/2f))/25f;
		lookForWinner();
	}

	private void lookForWinner() {
		if(scoreBalance < -mHeight/2f){
			TGamePointers.gameActivity.gameOver(TPlayerID.player2);	
		}else if(scoreBalance > mHeight/2f){
			TGamePointers.gameActivity.gameOver(TPlayerID.player1);
		}
		
	}

	@Override
	public void reset() {
		
	}
	
}
