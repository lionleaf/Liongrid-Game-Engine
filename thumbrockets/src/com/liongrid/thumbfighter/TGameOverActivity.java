package com.liongrid.thumbfighter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class TGameOverActivity extends Activity {
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameover);
		TextView text = (TextView) findViewById(R.id.gameOverText);
		
		Bundle extras = getIntent().getExtras();
		
		int winner = extras.getInt("com.liongrid.thumbfighter.winner", -1);
		
		text.setText("Player "+winner+" won!");
		
		
		
	}
}
