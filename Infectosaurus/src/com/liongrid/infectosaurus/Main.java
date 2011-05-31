package com.liongrid.infectosaurus;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class Main extends Activity {
	public static final String TAG = "Infectosaurus";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"In Main");
        Intent i;
        i = new Intent(this, GameBoard.class);
        startActivity(i);
    }
}