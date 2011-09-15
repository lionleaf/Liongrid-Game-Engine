package com.liongrid.thumbfighter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TMainMenuActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		Button playButt = (Button) findViewById(R.id.playButt);
		
		playButt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(TMainMenuActivity.this, TGameActivity.class);
				startActivity(i);
			}
		});
	}
}
