package com.liongrid.infectosaurus;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Camera;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.HUDButton;
import com.liongrid.gameengine.RenderSystem;
import com.liongrid.gameengine.Shape;
import com.liongrid.gameengine.Texture;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.gameengine.tools.Vector2;

public class HUDInfectoButton extends HUDButton implements Shape.Circle{
	
	private RenderSystem renderSystem;
	private InfectoGameObjectHandler objectHandler;
	
	public int width;
	public int height;
	
	private DrawableBitmap pressed;
	private DrawableBitmap unPressed;
	private boolean isPressed = false;
	private Vector2 centerPos;

	
	public HUDInfectoButton() {
		TextureLibrary texLib = gamePointers.textureLib;
		Texture tex;
		tex = texLib.allocateTexture(R.drawable.bluecircle);
		width = 100;
		height = 100;
		pressed = new DrawableBitmap(tex, width, height);
		tex = texLib.allocateTexture(R.drawable.redcircle);
		unPressed = new DrawableBitmap(tex, width, height);
		
		pos.x = Camera.screenWidth - width;
		centerPos = new Vector2((float) (pos.x + width/2.0), (float) (pos.y + height/2.0));
	}

	@Override
	public void update(float dt, BaseObject parent) {
		renderSystem = BaseObject.gamePointers.renderSystem; 
		objectHandler = GameActivity.infectoPointers.gameObjectHandler;
		
		if(isPressed) renderSystem.scheduleForDraw(pressed,   pos.x, pos.y, true);
		else          renderSystem.scheduleForDraw(unPressed, pos.x, pos.y, true);
	}
	
	public Vector2 getPos() {
		return centerPos;
	}

	public int getShape() {
		return Shape.CIRCLE;
	}

	@Override
	public void onPress() {
		isPressed = isPressed ? false : true;
	}

	@Override
	public void reset() {
		
	}

	public float getRadius() {
		return (float) (width/2.0);
	}

	
}
