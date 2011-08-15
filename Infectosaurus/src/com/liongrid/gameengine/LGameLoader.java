package com.liongrid.gameengine;

import android.os.Handler;
import android.widget.ProgressBar;


public class LGameLoader implements Runnable {
	private LSurfaceViewPanel panel;
	protected Handler mHandler;
	protected ProgressBar mProgressBar;
	
	public LGameLoader(LSurfaceViewPanel panel, Handler handler, ProgressBar progressBar){
		this.panel = panel;
		mProgressBar = progressBar;
		mHandler = handler;
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
	}
	
	protected void postProgress(final int value){
		mHandler.post(new Runnable(){

			public void run() {
				mProgressBar.setProgress(value);		
			}
			
		});
	}

}
