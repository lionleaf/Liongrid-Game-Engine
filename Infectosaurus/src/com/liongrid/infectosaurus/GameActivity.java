package com.liongrid.infectosaurus;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCamera;
import com.liongrid.gameengine.LGameActivityInterface;
import com.liongrid.gameengine.LGameThread;
import com.liongrid.gameengine.LInput;
import com.liongrid.gameengine.LButton;
import com.liongrid.gameengine.LView;
import com.liongrid.gameengine.LPanel;
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
import android.os.PowerManager;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
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

	LPanel panel;
	private GestureDetector gestureDetector;

	private static final boolean useScreenshot = false;
	public static InfectoPointers infectoPointers;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");

		infectoPointers = new InfectoPointers();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.d(Main.TAG,"In GameActivity onCreate");

		setScreenDimensionsAndScale();

		panel = new LPanel(this);
		if(savedInstanceState == null){
			panel.init();
			init();
		}else{
			LBaseObject.gamePointers.panel = panel;
		}



		panel.startGame();
		panel.setRender();
		preLoadTextures();

		setContentView(panel);

		Bundle extras = getIntent().getExtras();
		//TODO try catch and alert!!!!! on getint
		int difficulty = extras.getInt("com.liongrid.infectosaurus.difficulty");
		int pop = extras.getInt("com.liongrid.infectosaurus.population");
		infectoPointers.difficulty = difficulty;
		GameActivity.infectoPointers.NumberOfHumans = pop;

		
		CONTEXT = this;
		LBaseObject.gamePointers.map.spawnNPCs(pop,  difficulty);


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

	private void init() {
		infectoPointers.gameObjectHandler = new InfectoGameObjectHandler();
		infectoPointers.gameStatus = new GameStatus();
		infectoPointers.spawnPool = new SpawnPool();
		infectoPointers.curGameActivity = this;
		infectoPointers.situationHandler = new SituationHandler(10, LBaseObject.gamePointers.map);
		panel.addToRoot(infectoPointers.gameObjectHandler);
		panel.addToRoot(infectoPointers.gameStatus);
		
		InputInfectosaurus gameInput = new InputInfectosaurus();
		LView hudInput = new LButton();
		panel.addToRoot(hudInput);
		gestureDetector = new GestureDetector(this, new LInput(hudInput,gameInput));
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//outState.putSerializable("GamePointers", (Serializable) LBaseObject.gamePointers);
	}

	/**
	 * This should be placed somewhere else later. 
	 * But you have to load the textures to be used in a level!
	 *  Before you start up!
	 */
	public void preLoadTextures(){
		LTextureLibrary tLib = LBaseObject.gamePointers.textureLib;
		tLib.allocateTexture(R.drawable.spheremonster01);
		tLib.allocateTexture(R.drawable.mann1);
		tLib.allocateTexture(R.drawable.ants);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Gesture detection
		if (gestureDetector.onTouchEvent(event)) {
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
		panel.finish();
		super.finish();
	}

	@Override
	protected void onPause(){
		super.onPause();
		Log.d("Infectosaurus", "onPause()");
		wl.release();
		panel.onPause();
		saveData(getApplicationContext());
	}

	@Override
	protected void onResume(){
		super.onResume();
		Log.d("Infectosaurus", "onResume()"); 
		wl.acquire();
		panel.onResume();
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
}
