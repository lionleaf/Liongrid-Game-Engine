package com.liongrid.infectosaurus.hudobjects;

import com.liongrid.gameengine.BaseObject;

public abstract class HUDButton extends HUDObject{

	@Override
	public void update(float dt, BaseObject parent) {
		
	}

	public abstract void onPress();
}
