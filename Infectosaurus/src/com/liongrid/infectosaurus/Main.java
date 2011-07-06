package com.liongrid.infectosaurus;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Main extends Activity {
	public static final String TAG = "Infectosaurus";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"In Main");
        setContentView(R.layout.main);
        
        View startButton = findViewById(R.id.startGameButton);
        final SeekBar diffBar = (SeekBar) findViewById(R.id.difficultyBar);
        final TextView diffText = (TextView) findViewById(R.id.difficultyLabel);
        
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
        
        startButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent i;
		        i = new Intent(v.getContext(), GameActivity.class);
		        i.putExtra("com.liongrid.infectosaurus.difficulty", diffBar.getProgress());
		        startActivity(i);
			}
        	
        });
        
        View upgradeButton = findViewById(R.id.upgradeButton);
        upgradeButton.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				Intent i;
		        i = new Intent(v.getContext(), UpgradeActivity.class);
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
    
    @Override
    protected void onPause() {
    	super.onPause();
    	GameActivity.saveData(getApplicationContext());
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	GameActivity.loadData(getApplicationContext());
    }
}