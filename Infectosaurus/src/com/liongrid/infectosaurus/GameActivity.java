package com.liongrid.infectosaurus;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Camera;
import com.liongrid.gameengine.GameActivityInterface;
import com.liongrid.gameengine.Panel;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.gameengine.Upgrade;
import com.liongrid.infectosaurus.map.Level;
import com.liongrid.infectosaurus.upgrades.InfectosaurusUpgrade;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.Window;

/**
 * @author Lastis
 *		This activity is the upper class for the whole game play
 */
public class GameActivity extends Activity implements GameActivityInterface{
	
	private static final String SAVE_PREF_NAME = "infectoSave";

	public static Context CONTEXT;
	
	//To keep screen alive
	private PowerManager.WakeLock wl;
	
	Panel panel;
	private GestureDetector gestureDetector;
	public static InfectoPointers infectoPointers;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
		
		infectoPointers = new InfectoPointers();
		gestureDetector = new GestureDetector(this, new InputSystem());
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.d(Main.TAG,"In GameActivity onCreate");
		
		setScreenDimensionsAndScale();

		
		panel = new Panel(this);
		if(savedInstanceState == null){
			panel.init();
			init();
			
		}else{
			BaseObject.gamePointers.panel = panel;
		}
		

		
		panel.startGame();
		panel.setRender();
		preLoadTextures();
		
		setContentView(panel);
		
		Bundle extras = getIntent().getExtras();
		int difficulty = extras.getInt("com.liongrid.infectosaurus.difficulty");
		infectoPointers.difficulty = difficulty;
		
		//TODO try catch and alert!!!!! on getint
		CONTEXT = this;
		BaseObject.gamePointers.level.spawnNPCs(
	    		GameActivity.infectoPointers.NUMBER_OF_HUMANS,  difficulty);
		
		
	}

	
	
	public static void loadData(Context context){
		
		SharedPreferences data = context.getSharedPreferences(SAVE_PREF_NAME, 0);
		InfectosaurusUpgrade[] upgrades = InfectosaurusUpgrade.values();
		int upgradeCount = upgrades.length; 
		for (int i = 0; i < upgradeCount; i++) {
			Upgrade upgrade = upgrades[i].get();
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
			Upgrade upgrade = upgrades[i].get();
			editor.putInt(upgrades[i].name(), upgrade.getRank());
		}
		editor.commit();
	}
	
	private void init() {
		infectoPointers.gameObjectHandler = new InfectoGameObjectHandler();
		infectoPointers.HUDObjectHandler = new HUDObjectHandler();
		infectoPointers.gameStatus = new GameStatus();
		infectoPointers.curGameActivity = this;
		panel.addToRoot(infectoPointers.gameObjectHandler);
		panel.addToRoot(infectoPointers.HUDObjectHandler);
		panel.addToRoot(infectoPointers.gameStatus);
		
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//outState.putSerializable("GamePointers", (Serializable) BaseObject.gamePointers);
	}
	
	/**
	 * This should be placed somewhere else later. 
	 * But you have to load the textures to be used in a level!
	 *  Before you start up!
	 */
	public void preLoadTextures(){
		TextureLibrary tLib = BaseObject.gamePointers.textureLib;
		tLib.allocateTexture(R.drawable.spheremonster01);
		tLib.allocateTexture(R.drawable.mann1);
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
		Camera.init(display.getHeight(), 
					display.getWidth(), 
					Level.TILE_SIZE);
		Camera.setUnitsPerHeight(12);
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
}
