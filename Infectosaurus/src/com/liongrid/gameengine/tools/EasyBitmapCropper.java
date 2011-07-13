package com.liongrid.gameengine.tools;

import com.liongrid.gameengine.Texture;

public class EasyBitmapCropper {
	
	public static void crop(Texture tex, int index){
		int lengthX = (tex.bitmapWidth  - tex.initialX)%tex.width;
		int lengthY = (tex.bitmapHeight - tex.initialY)%tex.height;
		int j = index%lengthX;
		int i = index - j*lengthX;
		if(i >= lengthX || j >= lengthY) return;
		
		tex.x = tex.initialX + i*tex.width;
		tex.y = tex.initialY + j*tex.height;
	}
}
