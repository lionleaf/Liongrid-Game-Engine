package com.liongrid.gameengine;

import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

import com.liongrid.infectosaurus.InputInfectoHUD;
import com.liongrid.infectosaurus.InputInfectosaurus;
import com.liongrid.infectosaurus.map.Level;

public class Input extends SimpleOnGestureListener{

	private InputInfectosaurus gameInput;
	private InputInfectoHUD hudInput;

	public Input(InputInfectoHUD hudInput, InputInfectosaurus gameInput) {
		this.gameInput = gameInput;
		this.hudInput = hudInput;
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
		if(hudInput.onSingleTapUp(event)) return true;
		if(gameInput.onSingleTapUp(event)) return true;
		return false;
	}
	
	
	@Override
	public boolean onDown(MotionEvent e) {
		if(hudInput.onDown(e)) return true;
		if(gameInput.onDown(e)) return true;
		return false;
	}
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, 
			float distanceX, float distanceY) {
		
		if(hudInput.onScroll(e1, e2, distanceX, distanceY)) return true;
		if(gameInput.onScroll(e1, e2, distanceX, distanceY)) return true;
		return false;
		
	}
}
