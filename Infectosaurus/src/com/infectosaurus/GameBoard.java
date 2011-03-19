package com.infectosaurus;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * @author Lastis
 *		This activity is the upper class for the whole gameplay
 *		It cointains a GameThread to do calculations on objects and
 *		a surface class to render the objects on the screen
 */
public class GameBoard extends Activity{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new Panel(this));
    }
}
