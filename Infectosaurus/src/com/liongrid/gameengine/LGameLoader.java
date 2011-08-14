package com.liongrid.gameengine;

import android.os.Handler;
import android.widget.ProgressBar;

import com.liongrid.infectosaurus.map.Map;
import com.liongrid.infectosaurus.map.TileSet;

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
		panel.init();
		init();
	}
	
	/**
	 * Initiates the game engine! 
	 * Overriden methods must remember to call super.init()!
	 */
	protected void init(){
		LBaseObject.gamePointers = new LGamePointers();
	    LGamePointers.textureLib = new LTextureLibrary();
	    LGamePointers.renderSystem = new LRenderSystem();
	    postProgress(5);
	    LGamePointers.panel = panel;
	    LGamePointers.root = new LObjectHandler();
	    LGamePointers.tileSet = new TileSet();
	    LGamePointers.map = new Map();
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
