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
		TGamePointers.gameObjectHandler = new TGameObjectHandler();
		
		
	}

	@Override
	protected void preLoadTextures() {
		LGamePointers.textureLib.allocateTexture(R.drawable.redrocket);
		LGamePointers.textureLib.allocateTexture(R.drawable.greenrocket);
		LGamePointers.textureLib.allocateTexture(R.drawable.green);
		
	}

	@Override
	protected void setupGame() {
		LGamePointers.root.add(TGamePointers.gameObjectHandler);
		TGamePointers.gameStatus = new TGameStatus();
		LGamePointers.root.add(TGamePointers.gameStatus);
		spawnStuff();		
		LGamePointers.gameThread.setGameClickListener(new TTouchEventListener());
	}
	
	private void spawnStuff(){
		
	}


}
