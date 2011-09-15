package com.liongrid.thumbfighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		
		Button mainMenuButt = (Button) findViewById(R.id.mainMenuButt);
		Button restartButt = (Button) findViewById(R.id.playAgainButt);
		
		
		restartButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(TGameOverActivity.this, TGameActivity.class);
				startActivity(i);
			}
		});
		
		mainMenuButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(TGameOverActivity.this, TMainMenuActivity.class);
				startActivity(i);
			}
		});
		
	}
}
