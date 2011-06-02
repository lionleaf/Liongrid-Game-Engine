package com.liongrid.infectosaurus;


import com.liongrid.gameengine.GameActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class Main extends Activity {
	public static final String TAG = "Infectosaurus";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"In Main");
        setContentView(R.layout.main);
        
        View startButton = findViewById(R.id.startGameButton);
        startButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent i;
		        i = new Intent(v.getContext(), GameActivity.class);
		        startActivity(i);
			}
        	
        });
        
        View exitButton = findViewById(R.id.exitGameButton);
        exitButton.setOnClickListener(new OnClickListener(){
        	
			public void onClick(View v) {
				Main.this.finish();
			}
        	
        });
        
    }
}