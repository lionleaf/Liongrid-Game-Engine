package com.liongrid.gameengine;

import java.io.Serializable;


/**
 * @author Lionleaf
 * This is a class that holds a few pointers to the most important
 * objects in the game. The current object of this class can be 
 * accessed through the static field LBaseObject.gamePointers
 */
public class LGamePointers implements Serializable {

	public static LGameThread gameThread;
	public static LObjectHandler root;
	public static LRenderSystem renderSystem;
	public static LRenderingThread renderThread;
	public static volatile LSurfaceViewPanel panel;
	public static LMap map;
	public static LTileSet tileSet;
	public static LTextureLibrary textureLib;
	public static LAudio audio;
	
	public static void resetAll(){
		gameThread = null;
		root = null;
		renderSystem = null;
		renderThread = null;
		panel = null;
		map = null;
		tileSet = null;
		textureLib = null;
	}
}
