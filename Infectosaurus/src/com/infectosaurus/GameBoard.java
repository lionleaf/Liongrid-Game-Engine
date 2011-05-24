package com.infectosaurus;

import java.io.Serializable;

import com.infectosaurus.map.Level;

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
public class GameBoard extends Activity{
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
		panel.init(); 
		panel.startGame();

		setContentView(panel);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
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
		Camera.screenWidth = display.getWidth();
		Camera.screenHeight = display.getHeight();
		// 5 tiles width is the screen dimension 
		Camera.scale = Camera.screenWidth/(5*Level.TILE_SIZE);
	}

	@Override
	protected void onPause(){
		super.onPause();
		panel.onPause();
	}

	@Override
	protected void onResume(){
		super.onResume();
		panel.onResume();
	}
}
