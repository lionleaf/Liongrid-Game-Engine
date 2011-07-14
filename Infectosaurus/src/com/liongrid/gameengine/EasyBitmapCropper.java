package com.liongrid.gameengine;

import android.util.Log;

import com.liongrid.infectosaurus.Main;
import com.liongrid.infectosaurus.R;

public class EasyBitmapCropper {
	
	public static Texture crop(Texture tex, int squareX, int squareY){
		TextureLibrary texLib = BaseObject.gamePointers.textureLib;
		int x = tex.width * squareX;
		int y = tex.height * squareY;
		Texture returnTex = 
			texLib.allocateTexture(tex.resource, x, y, tex.width, tex.height);
		return returnTex;
	}
}
