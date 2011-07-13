package com.liongrid.infectosaurus;

import com.liongrid.gameengine.DrawableBitmap;

public class EasyBitmapCropper {
	
	public static void crop(DrawableBitmap drawing, int index, int left, int bottom, 
			int width, int height){
		drawing.setCrop(left, height - bottom, width, -height);
	}

}
