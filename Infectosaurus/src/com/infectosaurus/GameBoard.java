package com.infectosaurus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

/**
 * @author Lastis
 *		This activity is the upper class for the whole game play
 */
public class GameBoard extends Activity{
	private static final String TAG = "MyActivity";
	Panel panel;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        panel = new Panel(this);
        setContentView(panel);
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
