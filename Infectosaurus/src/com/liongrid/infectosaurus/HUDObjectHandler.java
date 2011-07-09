package com.liongrid.infectosaurus;

import com.liongrid.gameengine.Collision;
import com.liongrid.gameengine.HUDButton;
import com.liongrid.gameengine.HUDObject;
import com.liongrid.gameengine.ObjectHandler;
import com.liongrid.gameengine.ObjectHandlerInterface;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.gameengine.tools.Vector2;

public class HUDObjectHandler extends ObjectHandler<HUDObject>{
	private static final int DEFAULT_CAPCITY = ObjectHandlerInterface.DEFAULT_CAPACITY;
	
	private FixedSizeArray<HUDButton> HUDButtons = 
			new FixedSizeArray<HUDButton>(DEFAULT_CAPCITY);

	public HUDObjectHandler() {
		add(new HUDScore());
	}
	
	public void addButton(HUDButton button){
		add(button);
		HUDButtons.add(button);
	}
	
	public HUDButton getButtonAt(Vector2 pos){
		Object[] array = HUDButtons.getArray();
		for (int i = 0; i < array.length; i++) {
			HUDButton button = (HUDButton) array[i];
			if(Collision.collides(pos, button)) return button;
		}
		return null;
	}
}
