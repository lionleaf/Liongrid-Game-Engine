package com.liongrid.gameengine;

import java.io.Serializable;

import com.liongrid.infectosaurus.Main;
import com.liongrid.infectosaurus.map.Level;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;

/**
 * @author Lastis
 *		This activity is the upper class for the whole game play
 */
public class GameActivity extends Activity{
	Panel panel;
	private GestureDetector gestureDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		gestureDetector = new GestureDetector(this, new InputSystem());

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.d(Main.TAG,"In GameBoards onCreate");
		
		setScreenDimensionsAndScale();

		panel = new Panel(this);
		if(savedInstanceState == null){
			panel.init(); 
			
		}else{
			
			BaseObject.gamePointers.panel = panel;
			//BaseObject.gamePointers = (GamePointers)
				//savedInstanceState.getSerializable("GamePointers");
		}
		panel.startGame();
		panel.setRender();
		setContentView(panel);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//outState.putSerializable("GamePointers", (Serializable) BaseObject.gamePointers);
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Gesture detection
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return false;
	}

	private void setScreenDimensionsAndScale() {
		
		Display display = getWindowManager().getDefaultDisplay(); 
		/* Now we can retrieve all display-related infos */
		Camera.screenHeight = display.getHeight();
		Camera.screenWidth = display.getWidth();
		Camera.scale = Camera.screenHeight/((float)Camera.TILES_PER_WIDTH*Level.TILE_SIZE);
	}

	@Override
	protected void onPause(){
		super.onPause();
		Log.d("Infectosaurus", "onPause()");
		
		panel.onPause();
	}

	@Override
	protected void onResume(){
		super.onResume();
		Log.d("Infectosaurus", "onResume()"); 
		panel.onResume();
	}
}
