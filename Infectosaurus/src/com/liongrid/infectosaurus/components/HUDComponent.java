package com.liongrid.infectosaurus.components;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.DrawableObject;
import com.liongrid.gameengine.GameObject;

public class HUDComponent extends Component<GameObject>{

	private DrawableObject defaultDrawing;
	private int mY;
	private int mX;

	public HUDComponent(DrawableObject drawing, int x, int y) {
		defaultDrawing = drawing;
		mX = x;
		mY = y;
	}
	
	@Override
	public void update(float dt, GameObject parent) {
		DrawableObject toDraw = defaultDrawing;
		BaseObject.gamePointers.renderSystem.scheduleForDraw(toDraw, mX, mY);
	}

}
