package com.liongrid.infectosaurus;

import android.util.Log;

import com.liongrid.gameengine.Collision;
import com.liongrid.gameengine.HUDButton;
import com.liongrid.gameengine.HUDObject;
import com.liongrid.gameengine.ObjectHandler;
import com.liongrid.gameengine.ObjectHandlerInterface;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.gameengine.tools.Vector2;

public class HUDObjectHandler extends ObjectHandler<HUDObject>{
	private static final int DEFAULT_CAPCITY = ObjectHandlerInterface.DEFAULT_CAPACITY;
	
	private FixedSizeArray<HUDButton> hudButtons = 
			new FixedSizeArray<HUDButton>(DEFAULT_CAPCITY);

	public HUDObjectHandler() {
		add(new HUDScore());
		addButton(new HUDInfectoButton());
	}
	
	public void addButton(HUDButton button){
		add(button);
		hudButtons.add(button);
	}
	
	public HUDButton getButtonAt(Vector2 pos){
		Object[] array = hudButtons.getArray();
		for (int i = 0; i < array.length; i++) {
			HUDButton button = (HUDButton) array[i];
			if(Collision.collides(pos, button)) return button;
		}
		return null;
	}
	
	public HUDButton getButtonAt(float x, float y){
		Object[] array = hudButtons.getArray();
		int length = hudButtons.getCount();
		for (int i = 0; i < length; i++) {
			HUDButton button = (HUDButton) array[i];
			if(Collision.collides(x, y, button)) return button;
		}
		return null;
	}
}
