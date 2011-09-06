package com.liongrid.gameengine;

import com.liongrid.infectomancer.IGamePointers;

import android.os.Handler;
import android.widget.ProgressBar;


public abstract class LGameLoader implements Runnable {
	
	public interface LGameLoadedCallback{
		public void onGameLoaded();
	}
	
	protected LSurfaceViewPanel panel;
	protected Handler mHandler;
	protected ProgressBar mProgressBar;
	protected LGameLoadedCallback mCallback;
	
	public LGameLoader(LSurfaceViewPanel panel,LGameLoadedCallback callback, Handler handler, ProgressBar progressBar){
		this.panel = panel;
		mProgressBar = progressBar;
		mHandler = handler;
		mCallback = callback;
	}
	
	public synchronized void run() {
		LGamePointers.resetAll();
		panel.init();
		init();
	}
	
	/**
	 * Initiates the game engine! 
	 * Overriden methods must remember to call super.init()!
	 */
	protected void init(){
	    instantiateEngineClasses();
	    preLoadTextures();
		setupGame();
		startGame();
	}
	
	

	

	protected final void instantiateEngineClasses(){
		LGamePointers.textureLib = new LTextureLibrary();
	    LGamePointers.renderSystem = new LRenderSystem();
	    postProgress(5);
	    LGamePointers.panel = panel;
	    LGamePointers.root = new LObjectHandler();
	    LGamePointers.tileSet = new LTileSet();
	    LGamePointers.map = new LMap();
	    postProgress(35);
	    LGamePointers.gameThread = new LGameThread();
	    LGamePointers.renderThread = new LRenderingThread();
	    instantiateGameClasses();
	}
	
	/**
	 * Override this to instantiate classes in the game. Will be called right after
	 * the game engine classes has been instantiated. 
	 */
	protected abstract void instantiateGameClasses();

	protected abstract void preLoadTextures(); 
	
	protected abstract void setupGame();

	
	protected final void startGame() {
		LGamePointers.panel.startGame();
		LGamePointers.panel.setRender();
		
		mHandler.post(new Runnable(){
			public void run(){
				mCallback.onGameLoaded();
			}
		});
	}
	
	
	
	protected void postProgress(final int value){
		if(mHandler == null || mProgressBar == null){
			return;
		}
		mHandler.post(new Runnable(){

			public void run() {
				mProgressBar.setProgress(value);		
			}
			
		});
	}

}
