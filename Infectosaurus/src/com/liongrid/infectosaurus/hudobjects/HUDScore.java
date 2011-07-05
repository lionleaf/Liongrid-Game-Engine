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
	private InfectoGameObjectHandler oh;
	private RenderSystem rs;
	private int sw;
	private int sh;
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
		allocateNumbers(64, 64);
		drawing = new DrawableBitmap(tex, 256*2, 32*2);
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
		rs = BaseObject.gamePointers.renderSystem; 
		oh = GameActivity.infectoPointers.gameObjectHandler;
		sh = Camera.screenHeight;
		sw = Camera.screenWidth;
		
		rs.scheduleForDraw(drawing, sw - 300, 
									sh - 64, true);
		drawScore(oh.NUMBER_OF_HUMANS - oh.mCH.getCount(Team.Human.ordinal()));
	}

	private void drawScore(int count) {
		rs.scheduleForDraw(d0, sw - 172 + 64, sh - 64, true);
		rs.scheduleForDraw(d0, sw - 172 + 64*2, sh - 64, true);
		rs.scheduleForDraw(d0, sw - 172 + 64*3, sh - 64, true);
	}

	@Override
	public void reset() {
		
	}

}
