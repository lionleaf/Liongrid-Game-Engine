package com.liongrid.gameengine;

import android.util.Log;

import com.liongrid.infectosaurus.Main;
import com.liongrid.infectosaurus.R;


public class LButton extends LView{
	private DrawableBitmap starOn;
	private DrawableBitmap startOff;

	public LButton() {
		TextureLibrary texLib = gamePointers.textureLib;
		Texture tex1 = texLib.allocateTexture(R.drawable.bluecircle);
		Texture tex2 = texLib.allocateTexture(R.drawable.redcircle);
		starOn = new DrawableBitmap(tex1, 50, 50);
		startOff = new DrawableBitmap(tex2, 50, 50);
		setClickable(true);
	}

	@Override
	protected void onDraw(RenderSystem rs) {
		if(isPressed()){
			rs.scheduleForDraw(starOn, 0, 0, true);
		}
		else{
			rs.scheduleForDraw(startOff, 0, 0, true);
		}
	}

}
