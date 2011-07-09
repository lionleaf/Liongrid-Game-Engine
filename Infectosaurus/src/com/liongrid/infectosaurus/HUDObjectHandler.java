package com.liongrid.infectosaurus;

import com.liongrid.gameengine.HUDButton;
import com.liongrid.gameengine.HUDObject;
import com.liongrid.gameengine.ObjectHandler;
import com.liongrid.gameengine.ObjectHandlerInterface;

public class HUDObjectHandler extends ObjectHandler<HUDObject>{
	private static final int DEFAULT_CAPCITY = ObjectHandlerInterface.DEFAULT_CAPACITY;
	
	private HUDButton[]	HUDButtons = new HUDButton[DEFAULT_CAPCITY];

	public HUDObjectHandler() {
		
		add(new HUDScore());
	}
	
	public HUDButton getButton(int x, int y){
		return null;
	}
}
