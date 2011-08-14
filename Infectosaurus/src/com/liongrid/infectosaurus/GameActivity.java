package com.liongrid.infectosaurus;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCamera;
import com.liongrid.gameengine.LGameActivityInterface;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LGameThread;
import com.liongrid.gameengine.LInput;
import com.liongrid.gameengine.LButton;
import com.liongrid.gameengine.LView;
import com.liongrid.gameengine.LSurfaceViewPanel;
import com.liongrid.gameengine.LTextureLibrary;
import com.liongrid.gameengine.LUpgrade;
import com.liongrid.infectosaurus.crowd.situations.SituationHandler;
import com.liongrid.infectosaurus.map.Map;
import com.liongrid.infectosaurus.upgrades.InfectosaurusUpgrade;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author Lastis
 *		This activity is the upper class for the whole game play
 */
public class GameActivity extends Activity implements LGameActivityInterface{

	private static final String SAVE_PREF_NAME = "infectoSave";

	public static Context CONTEXT;

	//To keep screen alive
	private PowerManager.WakeLock wl;


	private GestureDetector mGestureDetector;

	private static final boolean useScreenshot = false;
	public static InfectoPointers infectoPointers;

	private Handler mHandler;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(Main.TAG,"In GameActivity onCreate");
		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setScreenDimensionsAndScale();
		
		CONTEXT = this;
		setContentView(R.layout.loading_game);
		LSurfaceViewPanel panel = new LSurfaceViewPanel(this);
		if(savedInstanceState == null){
			mHandler = new Handler();
			ProgressBar progress = (ProgressBar) findViewById(R.id.loadingGameBar);
			progress.setIndeterminate(false);
			
			(new Thread(new IGameLoader(panel,this,mHandler,progress))).start();
		}else{
			LBaseObject.gamePointers.panel = panel;
			setContentView(LGamePointers.panel);
		}
		
		
	}

	public void onFinishGameLoad(){
		setUpInputHandler();
		Log.d("Infectosaurus", "Game loaded");
		setContentView(LGamePointers.panel);
	}
	
	private void setUpInputHandler(){
		InputInfectosaurus gameInput = new InputInfectosaurus();
		LView hudInput = new LButton();
		LGamePointers.panel.addToRoot(hudInput);
		
		setGestureDetector(new GestureDetector
				(this, new LInput(hudInput,gameInput)));
	}


	public static void loadData(Context context){

		SharedPreferences data = context.getSharedPreferences(SAVE_PREF_NAME, 0);
		InfectosaurusUpgrade[] upgrades = InfectosaurusUpgrade.values();
		int upgradeCount = upgrades.length; 
		for (int i = 0; i < upgradeCount; i++) {
			LUpgrade upgrade = upgrades[i].get();
			int newRank = data.getInt(upgrades[i].name(), -1);
			if(newRank < 0) continue;
			upgrade.setRank(newRank);
		}

		int coins = data.getInt("coins", -1);
		if(coins != -1){
			InfectoPointers.coins = data.getInt("coins", 0);
		}
	}

	public static void saveData(Context context){
		SharedPreferences data = context.getSharedPreferences(SAVE_PREF_NAME, 0);
		SharedPreferences.Editor editor = data.edit();

		editor.putInt("coins", InfectoPointers.coins);

		InfectosaurusUpgrade[] upgrades = InfectosaurusUpgrade.values();
		int upgradeCount = upgrades.length; 
		for (int i = 0; i < upgradeCount; i++) {
			LUpgrade upgrade = upgrades[i].get();
			editor.putInt(upgrades[i].name(), upgrade.getRank());
		}
		editor.commit();
	}



	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//outState.putSerializable("GamePointers", (Serializable) LBaseObject.gamePointers);
	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Gesture detection
		if (mGestureDetector.onTouchEvent(event)) {
			return true;
		}
		return false;
	}

	public void setScreenDimensionsAndScale() {
		Display display = getWindowManager().getDefaultDisplay(); 
		/* Now we can retrieve all display-related infos */
		LCamera.init(display.getHeight(), 
				display.getWidth(), 
				Map.TILE_SIZE);
		LCamera.setUnitsPerHeight(12);
	}

	@Override
	public void finish() {
		LBaseObject.gamePointers.panel.finish();
		super.finish();
	}

	@Override
	protected void onPause(){
		super.onPause();
		Log.d("Infectosaurus", "onPause()");
		wl.release();
		if(LBaseObject.gamePointers.panel != null){
			LBaseObject.gamePointers.panel.onPause();
		}
		saveData(getApplicationContext());
	}

	@Override
	protected void onResume(){
		super.onResume();
		Log.d("Infectosaurus", "onResume()"); 
		wl.acquire();
		if(LBaseObject.gamePointers.panel != null){
			LBaseObject.gamePointers.panel.onResume();
		}
		
		loadData(getApplicationContext());
	}



	public void roundOver(final int humansKilled, final int coinsGained) {

		if(useScreenshot){
			LBaseObject.gamePointers.renderThread.takeScreenShot();
			while(LBaseObject.gamePointers.renderThread.lastScreenshot == null){
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}


		LBaseObject.gamePointers.gameThread.stopRunning();


		runOnUiThread(new Runnable() {

			public void run() {
				setContentView(R.layout.round_over);
				
				if(useScreenshot){
					View mainView = findViewById(R.id.roundOverMainLayout);
					Bitmap bmp = LBaseObject.gamePointers.renderThread.lastScreenshot;
					if(bmp != null){
						mainView.setBackgroundDrawable(new BitmapDrawable(bmp));
					}
				}
				
				Button backButton = (Button) findViewById(R.id.returnButton);
				backButton.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						finish();
					}
				});
				
				TextView roundOverText = (TextView) findViewById(R.id.roundOverText);
				int totalCoins =  InfectoPointers.coins;
				roundOverText.setText("Round over! Humans killed: "+humansKilled+
						" Coins gained: " + coinsGained +
						". Total coins: " + totalCoins+". ");
			}
		});

	}



	public void setGestureDetector(GestureDetector gestureDetector) {
		this.mGestureDetector = gestureDetector;
		
	}
}
