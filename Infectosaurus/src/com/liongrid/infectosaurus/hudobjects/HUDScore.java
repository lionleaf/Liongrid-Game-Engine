package com.liongrid.infectosaurus.hudobjects;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Camera;
import com.liongrid.gameengine.CollisionHandler;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.RenderSystem;
import com.liongrid.gameengine.Texture;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.InfectoGameObjectHandler;
import com.liongrid.infectosaurus.Main;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.Team;

public class HUDScore extends HUDObject{

	private int mHumansLeft;

	@Override
	public void update(float dt, BaseObject parent) {
		InfectoGameObjectHandler oh = GameActivity.infectoPointers.gameObjectHandler;
		mHumansLeft = oh.NUMBER_OF_HUMANS - oh.mCH.getCount(Team.Human.ordinal());
		
		TextureLibrary texLib = gamePointers.textureLib;
		Texture tex = texLib.allocateTexture(R.drawable.spheremonster01);
		DrawableBitmap drawing = new DrawableBitmap(tex, 100, 100);
		
		RenderSystem rs = BaseObject.gamePointers.renderSystem; 
		rs.scheduleForDraw(drawing, Camera.screenWidth - 200, 
									Camera.screenHeight - 100, true);
	}

	@Override
	public void reset() {
		
	}

}
