package com.liongrid.gameengine;

import android.view.MotionEvent;

import com.liongrid.gameengine.tools.Vector2;


public abstract class HUDButton extends HUDObject implements Shape{

	/**
	 * A method to signal that the button has been pressed
	 */
	public abstract void onSingleTapUp();
	public abstract void onDown();
	public abstract void onShowPress();
}
