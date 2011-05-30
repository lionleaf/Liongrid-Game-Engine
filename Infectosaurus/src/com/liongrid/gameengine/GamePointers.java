package com.liongrid.gameengine;

import java.io.Serializable;

import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.Panel;
import com.liongrid.infectosaurus.map.Level;
import com.liongrid.infectosaurus.map.TileSet;
import com.liongrid.infectosaurus.tools.GameObjectHandler;
import com.liongrid.infectosaurus.tools.ObjectHandler;

/**
 * @author Lionleaf
 * This is a class that holds a few pointers to the most important
 * objects in the game. The current object of this class can be 
 * accessed through the static field BaseObject.gamePointers
 */
public class GamePointers implements Serializable {
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
