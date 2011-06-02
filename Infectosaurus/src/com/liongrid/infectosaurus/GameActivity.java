package com.liongrid.infectosaurus;

import java.io.Serializable;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Camera;
import com.liongrid.gameengine.GameActivityInterface;
import com.liongrid.gameengine.Panel;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.infectosaurus.map.Level;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;

/**
 * @author Lastis
 *		This activity is the upper class for the whole game play
 */
public class GameActivity extends Activity implements GameActivityInterface{
	Panel panel;
	private GestureDetector gestureDetector;
	public static InfectoPointers infectoPointers;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		infectoPointers = new InfectoPointers();
		gestureDetector = new GestureDetector(this, new InputSystem());
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.d(Main.TAG,"In GameActivity onCreate");
		
		setScreenDimensionsAndScale();

		panel = new Panel(this);
		
		
		if(savedInstanceState == null){
			panel.init();
			infectoPointers.gameObjectHandler = new InfectoGameObjectHandler();
			panel.addToRoot(infectoPointers.gameObjectHandler);
		}else{
			BaseObject.gamePointers.panel = panel;
		}
		
		panel.startGame();
		panel.setRender();
		preLoadTextures();
		
		
		
		setContentView(panel);
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
		Camera.screenHeight = display.getHeight();
		Camera.screenWidth = display.getWidth();
		Camera.scale = Camera.screenHeight/((float)Camera.TILES_PER_WIDTH*Level.TILE_SIZE);
	}

	@Override
	protected void onPause(){
		super.onPause();
		Log.d("Infectosaurus", "onPause()");
		
		panel.onPause();
	}

	@Override
	protected void onResume(){
		super.onResume();
		Log.d("Infectosaurus", "onResume()"); 
		panel.onResume();
	}
}
