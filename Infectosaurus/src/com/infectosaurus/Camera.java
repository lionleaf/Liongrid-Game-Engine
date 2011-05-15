package com.infectosaurus;

public class Camera {
	private static int[] cameraPosition = {0,0};
	public static int screenWidth;
	public static int screenHeight;
		
	static int[] getCameraPosition(){
		return cameraPosition;
	}
}
