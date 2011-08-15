package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.infectosaurus.IGamePointers;
import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.IMainMenuActivity;

import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author Lionleaf
 * This class is the main loop responsible for updating
 * game logic and generating a queue of objects for the 
 * rendering thread to render
 *
 */
public class LGameThread extends Thread { 
	private LObjectHandler<?> root;

	private LFixedSizeArray<MotionEvent> mTouchEventQueue = 
		new LFixedSizeArray<MotionEvent>(32);

	private long dt;
	private long currentTime;
	private long lastTime = -1;

	private boolean running = true;
	LRenderSystem renderSystem;
	LRenderingThread renderThread;

	private Object updateLock;
	private Object touchLock = new Object();

	private static final int MIN_REFRESH_TIME = 16; //ms, 16 gives 60fps
	//ms, if the dt is lower than this, don't update this cycle
	private static final int MIN_UPDATE_MS = 12; 
	private static final float MAX_TIMESTEP = 0.1f; //seconds

	private volatile boolean paused = false;



	public LGameThread() {
		updateLock = new Object();
		root = LGamePointers.root;
		setName("LGameThread");
	}

	@Override
	public void run() {
		renderSystem = LGamePointers.renderSystem;
		renderThread = LGamePointers.renderThread;
		Log.d(IMainMenuActivity.TAG, "Starting LGameThread");
		while(running){
			//Make sure we don`t swap queues while renderer is rendering
			renderThread.waitDrawingComplete();
			waitWhilePaused();

			//calculateDT();
			currentTime = SystemClock.uptimeMillis();
			dt = currentTime - lastTime;
			if(lastTime == -1){
				lastTime = SystemClock.uptimeMillis();
				dt = 0; //Take no timestep if this is the first iteration
			}		

			long dtfinal = dt; 
			if(dt > MIN_UPDATE_MS){
				float dtsec = (currentTime - lastTime) * 0.001f;
				//We never want to take too big timesteps!
				if(dtsec > MAX_TIMESTEP){
					dtsec = MAX_TIMESTEP;
				}
				lastTime = currentTime;
				//Update all game logic
				synchronized(updateLock){
					handleTouchEvents();
					root.update(dtsec, null);
				}
				//Sends the previously completed renderQueue 
				//to the renderer, and gets a new empty one
				renderSystem.swap(renderThread);

				dtfinal = SystemClock.uptimeMillis() - currentTime;

			}
			//Take a small nap to get those touch events!
			try {
				Thread.sleep(Math.max(2,MIN_UPDATE_MS - dtfinal));
			} catch (InterruptedException e) {
				//Doesn`t matter
			}
		}
	}

	private void calculateDT() {

	}

	private synchronized void waitWhilePaused() {
		while(paused){
			try {
				Log.d(IMainMenuActivity.TAG, "Game is paused!");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void handleTouchEvents(){
		
		//Make sure nothing is added to the queue while we`re working on it
		synchronized(touchLock){
			Object[] rawArr = mTouchEventQueue.getArray();

			int cunt = mTouchEventQueue.getCount();
			for(int i = 0; i < cunt; i++){
				MotionEvent event = (MotionEvent) rawArr[i];
				doTouchEvent(event);
			}
			mTouchEventQueue.clear();
		}


	}

	private void doTouchEvent(MotionEvent event) {
		if(LGamePointers.currentSaurus != null) return;

		Infectosaurus inf = new Infectosaurus();
		LGamePointers.currentSaurus = inf;
		IGamePointers.gameObjectHandler.add(inf);


		float y = (LGamePointers.panel.getHeight() - event.getY()) / LCamera.scale;
		float x = event.getX() / LCamera.scale;
		LGamePointers.currentSaurus.pos.set(x + LCamera.pos.x, 
				y + LCamera.pos.y);
	}

	public void registerScreenTouch(MotionEvent event) {
		synchronized(touchLock){
			mTouchEventQueue.add(event);
		}
	}

	public void pause(){
		paused = true;
	}
	public void unpause(){
		paused = false;
		synchronized(this){
			notifyAll();
		}
	}

	public void onPause() {
		pause();
	}

	/**
	 * Stops the thread!
	 */
	public void stopRunning(){
		running = false;
	}

	public void onResume(){
		unpause();

	}
}
