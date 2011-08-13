package com.liongrid.gameengine;

import java.io.Serializable;

import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.map.Map;
import com.liongrid.infectosaurus.map.TileSet;

/**
 * @author Lionleaf
 * This is a class that holds a few pointers to the most important
 * objects in the game. The current object of this class can be 
 * accessed through the static field LBaseObject.gamePointers
 */
public class LGameEnginePointers implements Serializable {

	public LGameThread gameThread;
	public LObjectHandler root;
	public LRenderSystem renderSystem;
	public LRenderingThread renderThread;
	public LPanel panel;
	public Map map;
	public TileSet tileSet;
	public Infectosaurus currentSaurus;
	public LTextureLibrary textureLib;
}
