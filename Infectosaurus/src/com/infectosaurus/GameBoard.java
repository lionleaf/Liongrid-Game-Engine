package com.infectosaurus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;

/**
 * @author Lastis
 *		This activity is the upper class for the whole game play
 */
public class GameBoard extends Activity{
	private static final String TAG = "MyActivity";
	Panel panel;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log.d(TAG,"In GameBoard");
        
        setScreenDimensions();
		
        panel = new Panel(this);
        setContentView(panel);
        
    }
	
	private void setScreenDimensions() {
        Display display = getWindowManager().getDefaultDisplay(); 
		/* Now we can retrieve all display-related infos */
		Camera.screenWidth = display.getWidth();
		Camera.screenHeight = display.getHeight();	
		
		Log.d(TAG, "Height = " + Camera.screenHeight);
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
