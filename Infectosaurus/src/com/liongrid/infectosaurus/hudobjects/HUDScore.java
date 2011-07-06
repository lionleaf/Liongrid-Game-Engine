package com.liongrid.infectosaurus.hudobjects;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Camera;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.RenderSystem;
import com.liongrid.gameengine.Texture;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObjectHandler;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.Team;

public class HUDScore extends HUDObject{

	private DrawableBitmap drawing;
	private int mHumansLeft;
	private InfectoGameObjectHandler objectHandler;
	private RenderSystem renderSystem;
	private int screenWidth;
	private int screenHeight;
	private final int fontSize = 20;
	private final int paddingTop = 30;

	private DrawableBitmap d0;
	private DrawableBitmap d1;
	private DrawableBitmap d2;
	private DrawableBitmap d3;
	private DrawableBitmap d4;
	private DrawableBitmap d5;
	private DrawableBitmap d6;
	private DrawableBitmap d7;
	private DrawableBitmap d8;
	private DrawableBitmap d9;

	public HUDScore() {
		TextureLibrary texLib = gamePointers.textureLib;
		Texture tex = texLib.allocateTexture(R.drawable.humans_killed);
		allocateNumbers(fontSize, fontSize);
		drawing = new DrawableBitmap(tex, 8*fontSize, fontSize);
	}


	private void allocateNumbers(int width, int height) {
		TextureLibrary texLib = gamePointers.textureLib;
		Texture tex0 = texLib.allocateTexture(R.drawable.d0);
		Texture tex1 = texLib.allocateTexture(R.drawable.d1);
		Texture tex2 = texLib.allocateTexture(R.drawable.d2);
		Texture tex3 = texLib.allocateTexture(R.drawable.d3);
		Texture tex4 = texLib.allocateTexture(R.drawable.d4);
		Texture tex5 = texLib.allocateTexture(R.drawable.d5);
		Texture tex6 = texLib.allocateTexture(R.drawable.d6);
		Texture tex7 = texLib.allocateTexture(R.drawable.d7);
		Texture tex8 = texLib.allocateTexture(R.drawable.d8);
		Texture tex9 = texLib.allocateTexture(R.drawable.d9);

		d0 = new DrawableBitmap(tex0,width, height);
		d1 = new DrawableBitmap(tex1,width, height);
		d2 = new DrawableBitmap(tex2,width, height);
		d3 = new DrawableBitmap(tex3,width, height);
		d4 = new DrawableBitmap(tex4,width, height);
		d5 = new DrawableBitmap(tex5,width, height);
		d6 = new DrawableBitmap(tex6,width, height);
		d7 = new DrawableBitmap(tex7,width, height);
		d8 = new DrawableBitmap(tex8,width, height);
		d9 = new DrawableBitmap(tex9,width, height);
	}


	@Override
	public void update(float dt, BaseObject parent) {
		renderSystem = BaseObject.gamePointers.renderSystem; 
		objectHandler = GameActivity.infectoPointers.gameObjectHandler;
		screenHeight = Camera.screenHeight;
		screenWidth = Camera.screenWidth;

		renderSystem.scheduleForDraw(drawing, screenWidth - 200, 
				screenHeight - paddingTop, true);
		drawScore(GameActivity.infectoPointers.NumberOfHumans - objectHandler.mCH.getCount(Team.Human.ordinal()));
	}

	private void drawScore(int count) {

		DrawableBitmap firstDigit = getDigit(count%10);
		DrawableBitmap secondDigit = getDigit((count/10)%10);
		DrawableBitmap thirdDigit = getDigit((count/100)%10);;

		renderSystem.scheduleForDraw(thirdDigit, screenWidth - 200 + 8*fontSize , screenHeight - paddingTop, true);
		renderSystem.scheduleForDraw(secondDigit, screenWidth - 200 + 8*fontSize + fontSize / 2, screenHeight - paddingTop, true);
		renderSystem.scheduleForDraw(firstDigit, screenWidth - 200 + 8*fontSize + fontSize, screenHeight - paddingTop, true);
	}

	private DrawableBitmap getDigit(int i) {
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
