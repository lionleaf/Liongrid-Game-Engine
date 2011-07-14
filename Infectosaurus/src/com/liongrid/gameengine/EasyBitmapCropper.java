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
	
	public static int[]	moveCrop(Texture tex, int[] cropWorkspace, int index){
		int offsetX = cropWorkspace[0];
		while(offsetX > tex.width) offsetX -= tex.width;
		int offsetY = tex.height - cropWorkspace[1];
		while(offsetY > tex.height) offsetY -= tex.height;
		
		int cropWidth = cropWorkspace[2];
		int cropHeight = - cropWorkspace[3];
		
		int lengthX = (tex.width - offsetX)%cropWidth;
		int lengthY = (tex.height - offsetY)%cropHeight;
		
		int j = index%lengthX;
		int i = index - j*lengthX;
		if(i >= lengthX || j >= lengthY) return null;
		
		int[] newCropWorkspace = new int[4];
		newCropWorkspace[0] = offsetX + i*tex.width;
		newCropWorkspace[1] = tex.height - (offsetY + j*tex.height);
		newCropWorkspace[2] = cropWorkspace[2];
		newCropWorkspace[3] = cropWorkspace[3];
		return newCropWorkspace;
	}
}
