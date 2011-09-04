package com.liongrid.gameengine;

import com.liongrid.gameengine.view.LView;
import com.liongrid.infectomancer.R;


public class LButton extends LView{
	private LDrawableBitmap starOn;
	private LDrawableBitmap startOff;

	public LButton() {
		LTextureLibrary texLib = LGamePointers.textureLib;
		LTexture tex1 = texLib.allocateTexture(R.drawable.bluecircle);
		LTexture tex2 = texLib.allocateTexture(R.drawable.redcircle);
		starOn = new LDrawableBitmap(tex1, 50, 50);
		startOff = new LDrawableBitmap(tex2, 50, 50);
		setClickable(true);
	}

	@Override
	protected void onDraw(LRenderSystem rs) {
		if(isPressed()){
			rs.scheduleForDraw(starOn, 0, 0, true);
		}
		else{
			rs.scheduleForDraw(startOff, 0, 0, true);
		}
	}

}
