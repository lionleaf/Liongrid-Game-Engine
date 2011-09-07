package com.liongrid.thumbfighter;

import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ProgressBar;

import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LGameThread.TouchEventListener;
import com.liongrid.gameengine.components.LDrawableComponent;
import com.liongrid.gameengine.components.LMoveComponent;
import com.liongrid.gameengine.components.LTiltMovementComponent;
import com.liongrid.gameengine.LGameLoader;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LSurfaceViewPanel;

public class TGameLoader extends LGameLoader implements Runnable {

	public TGameLoader(LSurfaceViewPanel panel, LGameLoadedCallback callback,
			Handler handler, ProgressBar progressBar) {
		super(panel, callback, handler, progressBar);
	}

	@Override
	protected void instantiateGameClasses() {
		TGamePointers.spawnPool = new TSpawnPool();
		
	}

	@Override
	protected void preLoadTextures() {
		LGamePointers.textureLib.allocateTexture(R.drawable.redrocket);
		LGamePointers.textureLib.allocateTexture(R.drawable.greenrocket);
		
	}

	@Override
	protected void setupGame() {
		spawnStuff();		
		LGamePointers.gameThread.setGameClickListener(new TouchEventListener() {

			@Override
			public void onTouch(MotionEvent event) {
				if(event.getAction() != MotionEvent.ACTION_DOWN) return;
				TPlayerID pID;
				if(event.getY() < LGamePointers.panel.getHeight()/2.0){
					pID = TPlayerID.player2; 
				}else{
					pID = TPlayerID.player1;
				}
				LGameObject rocket = TGamePointers.spawnPool.spawnRocket(500, event.getX(), pID);
				LGamePointers.root.add(rocket);
			}
		});
	}
	
	private void spawnStuff(){
		
	}


}
