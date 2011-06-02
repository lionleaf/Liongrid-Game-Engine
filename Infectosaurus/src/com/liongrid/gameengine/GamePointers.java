package com.liongrid.gameengine;

import java.io.Serializable;

import com.liongrid.infectosaurus.InfectoGameObjectHandler;
import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.map.Level;
import com.liongrid.infectosaurus.map.TileSet;

/**
 * @author Lionleaf
 * This is a class that holds a few pointers to the most important
 * objects in the game. The current object of this class can be 
 * accessed through the static field BaseObject.gamePointers
 */
public class GamePointers implements Serializable {
	/**
	 * This pointer should be to a specific GameObjectHandler which will have to be casted
	 * when used. 
	 */
	public ObjectHandler gameObjectHandler;
	public GameThread gameThread;
	public ObjectHandler root;
	public RenderSystem renderSystem;
	public RenderingThread renderThread;
	public Panel panel;
	public Level level;
	public TileSet tileSet;
	public Infectosaurus currentSaurus;
	public TextureLibrary textureLib;
}
