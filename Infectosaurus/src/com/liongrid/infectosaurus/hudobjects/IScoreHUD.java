package com.liongrid.infectosaurus.hudobjects;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCamera;
import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LRenderSystem;
import com.liongrid.gameengine.LTexture;
import com.liongrid.gameengine.LTextureLibrary;
import com.liongrid.infectosaurus.IGameObjectHandler;
import com.liongrid.infectosaurus.IGamePointers;
import com.liongrid.infectosaurus.ITeam;
import com.liongrid.infectosaurus.R;

public class IScoreHUD extends LBaseObject{

	private LDrawableBitmap drawing;
	private IGameObjectHandler objectHandler;
	private LRenderSystem renderSystem;
	private int screenWidth;
	private int screenHeight;
	private final int fontSize = 20;
	private final int paddingTop = 30;
	private final int paddingRight = 200;

	private LDrawableBitmap d0;
	private LDrawableBitmap d1;
	private LDrawableBitmap d2;
	private LDrawableBitmap d3;
	private LDrawableBitmap d4;
	private LDrawableBitmap d5;
	private LDrawableBitmap d6;
	private LDrawableBitmap d7;
	private LDrawableBitmap d8;
	private LDrawableBitmap d9;

	public IScoreHUD() {
		LTextureLibrary texLib = LGamePointers.textureLib;
		LTexture tex = texLib.allocateTexture(R.drawable.humans_killed);
		allocateNumbers(fontSize, fontSize);
		drawing = new LDrawableBitmap(tex, 8*fontSize, fontSize);
	}


	private void allocateNumbers(int width, int height) {
		LTextureLibrary texLib = LGamePointers.textureLib;
		LTexture tex0 = texLib.allocateTexture(R.drawable.d0);
		LTexture tex1 = texLib.allocateTexture(R.drawable.d1);
		LTexture tex2 = texLib.allocateTexture(R.drawable.d2);
		LTexture tex3 = texLib.allocateTexture(R.drawable.d3);
		LTexture tex4 = texLib.allocateTexture(R.drawable.d4);
		LTexture tex5 = texLib.allocateTexture(R.drawable.d5);
		LTexture tex6 = texLib.allocateTexture(R.drawable.d6);
		LTexture tex7 = texLib.allocateTexture(R.drawable.d7);
		LTexture tex8 = texLib.allocateTexture(R.drawable.d8);
		LTexture tex9 = texLib.allocateTexture(R.drawable.d9);

		d0 = new LDrawableBitmap(tex0,width, height);
		d1 = new LDrawableBitmap(tex1,width, height);
		d2 = new LDrawableBitmap(tex2,width, height);
		d3 = new LDrawableBitmap(tex3,width, height);
		d4 = new LDrawableBitmap(tex4,width, height);
		d5 = new LDrawableBitmap(tex5,width, height);
		d6 = new LDrawableBitmap(tex6,width, height);
		d7 = new LDrawableBitmap(tex7,width, height);
		d8 = new LDrawableBitmap(tex8,width, height);
		d9 = new LDrawableBitmap(tex9,width, height);
	}


	@Override
	public void update(float dt, LBaseObject parent) {
		renderSystem = LGamePointers.renderSystem; 
		objectHandler = IGamePointers.gameObjectHandler;
		screenHeight = LCamera.screenHeight;
		screenWidth = LCamera.screenWidth;

		renderSystem.scheduleForDraw(drawing, screenWidth - paddingRight, 
											  screenHeight - paddingTop, true);
		drawScore(IGamePointers.NumberOfHumans - objectHandler.getCount(ITeam.Human));
	}

	private void drawScore(int count) {

		LDrawableBitmap firstDigit = getDigit(count%10);
		LDrawableBitmap secondDigit = getDigit((count/10)%10);
		LDrawableBitmap thirdDigit = getDigit((count/100)%10);;

		renderSystem.scheduleForDraw(thirdDigit, screenWidth - paddingRight + 8*fontSize , screenHeight - paddingTop, true);
		renderSystem.scheduleForDraw(secondDigit, screenWidth - paddingRight + 8*fontSize + fontSize / 2, screenHeight - paddingTop, true);
		renderSystem.scheduleForDraw(firstDigit, screenWidth - paddingRight + 8*fontSize + fontSize, screenHeight - paddingTop, true);
	}

	private LDrawableBitmap getDigit(int i) {
		switch(i){
		case 0:
			return d0;
		case 1:
			return d1;
		case 2:
			return d2;
		case 3:
			return d3;
		case 4:
			return d4;
		case 5:
			return d5;
		case 6:
			return d6;
		case 7:
			return d7;
		case 8:
			return d8;
		case 9:
			return d9;
		default:
			return null;
		}
	}


	@Override
	public void reset() {

	}

}
