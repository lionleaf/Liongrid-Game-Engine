package com.liongrid.thumbfighter;

import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LSurfaceViewPanel;
import com.liongrid.gameengine.LGameLoader.LGameLoadedCallback;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;

public class TGameActivity extends Activity implements LGameLoadedCallback {
	private Handler mHandler;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        LSurfaceViewPanel panel = new LSurfaceViewPanel(this);
        TGamePointers.gameActivity = this;
        
        if(savedInstanceState == null){
			mHandler = new Handler();
			(new Thread(new TGameLoader(panel, this, mHandler, null))).start();
		}else{
			LGamePointers.panel = panel;
			setContentView(LGamePointers.panel);
		}
    	setContentView(R.layout.main);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	LGamePointers.gameThread.registerScreenTouch(event);
    	return true;
    }
    

	@Override
	public void onGameLoaded() {
		setContentView(LGamePointers.panel);
	}
}