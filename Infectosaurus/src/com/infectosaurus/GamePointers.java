package com.infectosaurus;

import com.infectosaurus.map.Level;
import com.infectosaurus.map.TileSet;

/**
 * @author Lionleaf
 * This is a class that holds a few pointers to the most important
 * objects in the game. The current object of this class can be 
 * accessed through the static field BaseObject.gamePointers
 */
public class GamePointers {
	public GameThread gameThread;
	public GameObjectHandler gameObjectHandler;
	public ObjectHandler root;
	public RenderSystem renderSystem;
	public RenderingThread renderThread;
	public Panel panel;
	public Level level;
	public TileSet tileSet;
	public Infectosaurus currentSaurus;
}
