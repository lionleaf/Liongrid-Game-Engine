package com.infectosaurus;

import java.io.Serializable;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;

/**
 * @author Lastis
 *		This activity is the upper class for the whole game play
 */
public class GameBoard extends Activity{
	Panel panel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.d(Main.TAG,"In GameBoards onCreate");

		setScreenDimensions();

		panel = new Panel(this);
		panel.init(); 
		panel.startGame();

		setContentView(panel);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}

	private void setScreenDimensions() {
		Display display = getWindowManager().getDefaultDisplay(); 
		/* Now we can retrieve all display-related infos */
		Camera.screenWidth = display.getWidth();
		Camera.screenHeight = display.getHeight();	

		Log.d(Main.TAG, "Height = " + Camera.screenHeight);
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
