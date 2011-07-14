package com.liongrid.gameengine;

import android.util.Log;

import com.liongrid.infectosaurus.Main;
import com.liongrid.infectosaurus.R;

public class EasyBitmapCropper {
	
	public static int[] crop(Texture tex, int x, int y, int width, int height){
		int[] cropWorkspace = new int[4];
		cropWorkspace[0] = x;
		cropWorkspace[1] = tex.height - y;
		cropWorkspace[2] = width;
		cropWorkspace[3] = - height;
		return cropWorkspace;
	}
	
	public static int[]	moveCrop(Texture tex, int[] cropWorkspace, int squareX, int squareY){
		int x = cropWorkspace[0];
		while(x > tex.width) x -= tex.width;
		int y = tex.height - cropWorkspace[1];
		while(y > tex.height) y -= tex.height;
		
		int cropWidth = cropWorkspace[2];
		int cropHeight = - cropWorkspace[3];
		
		int[] newCropWorkspace = new int[4];
		newCropWorkspace[0] = x + squareX*cropWidth;
		newCropWorkspace[1] = tex.height - (y + squareY*cropHeight);
		newCropWorkspace[2] = cropWorkspace[2];
		newCropWorkspace[3] = cropWorkspace[3];
		return newCropWorkspace;
	}
	
	public static int[]	moveCrop(Texture tex, int[] cropWorkspace, int squareX, int squareY,
			int offsetX, int offsetY){
		int x = cropWorkspace[0];
		while(x > tex.width) x -= tex.width;
		int y = tex.height - cropWorkspace[1];
		while(y > tex.height) y -= tex.height;
		
		int cropWidth = cropWorkspace[2];
		int cropHeight = - cropWorkspace[3];
		
		int[] newCropWorkspace = new int[4];
		newCropWorkspace[0] = x + squareX*(cropWidth + offsetX);
		newCropWorkspace[1] = tex.height - (y + squareY*(cropHeight + offsetY));
		newCropWorkspace[2] = cropWorkspace[2];
		newCropWorkspace[3] = cropWorkspace[3];
		return newCropWorkspace;
	}
}
