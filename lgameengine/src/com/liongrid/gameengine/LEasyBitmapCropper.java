package com.liongrid.gameengine;

public class LEasyBitmapCropper {
	
	public static int[] cropWithWidth(int x, int y, int width, int height){
		int[] cropWorkspace = new int[4];
		cropWorkspace[0] = x;
		cropWorkspace[1] = y + height;
		cropWorkspace[2] = width;
		cropWorkspace[3] = - height;
		return cropWorkspace;
	}
	
	public static int[] cropWithPos(int topLeftX, int topLeftY, int botRightX, int botRightY){
		int width = botRightX - topLeftX;
		int height = botRightY - topLeftY;
		return cropWithWidth(topLeftX, topLeftY, width, height);
	}
	
	public static int[]	moveCrop(int[] cropWorkspace, int horisontalCnt, int verticalCnt){
		return moveCrop(cropWorkspace, horisontalCnt, verticalCnt, 0, 0);
	}
	
	public static int[]	moveCrop(int[] cropWorkspace, int horisontalCnt, int verticalCnt,
			int offsetX, int offsetY){
		
		int x = cropWorkspace[0];
		int y = cropWorkspace[1];
		int width = cropWorkspace[2];
		int height = cropWorkspace[3];
		
		int[] newCropWorkspace = new int[4];
		newCropWorkspace[0] = x + horisontalCnt * (width + offsetX);
		newCropWorkspace[1] = y + verticalCnt * (height + offsetY);
		newCropWorkspace[2] = cropWorkspace[2];
		newCropWorkspace[3] = cropWorkspace[3];
		return newCropWorkspace;
	}
}
