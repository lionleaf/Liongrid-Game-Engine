package com.liongrid.infectomancer;



import com.liongrid.infectomancer.upgrades.IUpgrade;
import com.liongrid.infectomancer.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class IMainMenuActivity extends Activity {
	public static final String TAG = "Infectosaurus";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"In IMainMenuActivity");
        setContentView(R.layout.main);
        
        View startButton = findViewById(R.id.startGameButton);
        final SeekBar diffBar = (SeekBar) findViewById(R.id.difficultyBar);
        final TextView diffText = (TextView) findViewById(R.id.difficultyLabel);
        final SeekBar popBar = (SeekBar) findViewById(R.id.popBar);
        final TextView popText = (TextView) findViewById(R.id.popTextView);
        
        diffBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
        	
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if(progress == 0){
					seekBar.setProgress(1);
					return;
				}
				diffText.setText(progress+"");
			}
		});
        
        popBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
        	
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				if(progress == 0){
					seekBar.setProgress(1);
					return;
				}
				popText.setText(progress+"");
				
			}
		});

        
        
        startButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent i;
		        i = new Intent(v.getContext(), IGameActivity.class);
		        i.putExtra("com.liongrid.infectomancer.difficulty", diffBar.getProgress());
		        i.putExtra("com.liongrid.infectomancer.population", popBar.getProgress());
		        startActivity(i);
			}
        	
        });
        
        View upgradeButton = findViewById(R.id.upgradeButton);
        upgradeButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent i;
		        i = new Intent(v.getContext(), IUpgradeActivity.class);
		        startActivity(i);
			}
        });
        
/*
        View statButton = findViewById(R.id.statButton);
        statButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i;
				i = new Intent(v.getContext(), IStatActivity.class);
				startActivity(i);
			}
		});
        */
        
        View exitButton = findViewById(R.id.exitGameButton);
        exitButton.setOnClickListener(new OnClickListener(){
        	
			public void onClick(View v) {
				IMainMenuActivity.this.finish();
			}
        	
        });
        
        
        View resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				resetProgress();
				
			}
		});
    }
    
    private void resetProgress(){
    	IGamePointers.coins = 0;
    	IUpgrade[] upgrades = IUpgrade.values();
    	for (int i = 0; i < upgrades.length; i++) {
			upgrades[i].get().setRank(0);
    	}
    	
    	IGameActivity.saveData(getApplicationContext());
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	IGameActivity.saveData(getApplicationContext());
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	IGameActivity.loadData(getApplicationContext());
    }
}