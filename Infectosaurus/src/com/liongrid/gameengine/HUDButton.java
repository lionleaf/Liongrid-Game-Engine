package com.liongrid.gameengine;


public abstract class HUDButton extends HUDObject implements Shape{

	@Override
	public void update(float dt, BaseObject parent) {
		
	}

	public abstract void onPress();
}
