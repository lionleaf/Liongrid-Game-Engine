package com.liongrid.helicoptergame;

import android.os.Handler;
import android.widget.ProgressBar;

import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LDrawableComponent;
import com.liongrid.gameengine.LGameLoader;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LObjectHandler;
import com.liongrid.gameengine.LSurfaceViewPanel;
import com.liongrid.gameengine.LTextureLibrary;

import com.liongrid.helicoptergame.R;

public class HGameLoader extends LGameLoader {


	public HGameLoader(LSurfaceViewPanel panel, LGameLoadedCallback callback,
			Handler handler, ProgressBar progressBar) {
		super(panel, callback, handler, progressBar);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void instantiateGameClasses() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void preLoadTextures() {
		LTextureLibrary tLib = LGamePointers.textureLib;
		tLib.allocateTexture(R.drawable.apache);	
	}

	@Override
	protected void setupGame() {
		LObjectHandler<LGameObject> oh = new LObjectHandler<LGameObject>();
		LGamePointers.root.add(oh);
		LGameObject helicopter = new LGameObject();
		helicopter.addComponent(new LDrawableComponent(new LDrawableBitmap(
				LGamePointers.textureLib.allocateTexture(R.drawable.apache), 256, 64)));
		helicopter.mPos.x = 70;
		helicopter.mPos.y = 70;
		oh.add(helicopter);
		
	}
	
	

}
