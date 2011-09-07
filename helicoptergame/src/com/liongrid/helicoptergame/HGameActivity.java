package com.liongrid.helicoptergame;

import com.liongrid.gameengine.LGameLoader.LGameLoadedCallback;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LSurfaceViewPanel;

import com.liongrid.helicoptergame.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class HGameActivity extends Activity implements LGameLoadedCallback{
	private Handler mHandler;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LSurfaceViewPanel panel = new LSurfaceViewPanel(this);
        
        if(savedInstanceState == null){
			mHandler = new Handler();
			(new Thread(new HGameLoader(panel, this, mHandler, null))).start();
		}else{
			LGamePointers.panel = panel;
			setContentView(LGamePointers.panel);
		}
    	setContentView(R.layout.main);
    }

	@Override
	public void onGameLoaded() {
		setContentView(LGamePointers.panel);
	}
}