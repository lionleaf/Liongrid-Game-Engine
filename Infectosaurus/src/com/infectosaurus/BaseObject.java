package com.infectosaurus;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.infectosaurus.components.Component;

public abstract class BaseObject {
	private static final int DEFAULT_SIZE = 10;
	private ArrayList<Component> gameComponents;
	private ArrayList<Component> renderComponents;
	
	BaseObject(){
		Log.d("GameBoard", "In BaseObject");
		gameComponents = new ArrayList<Component>(DEFAULT_SIZE);
		renderComponents = new ArrayList<Component>(DEFAULT_SIZE);
	}
	
	BaseObject(int size){
		gameComponents = new ArrayList<Component>(size);
		renderComponents = new ArrayList<Component>(size);
	}
	
	public void useComp4Game(){
		for(Component c: gameComponents) c.update4Game();
	}
	
	public void useComp4Renderer(GL10 gl){
		Log.d("GameBoard", "In BaseObject");
		for(Component c: renderComponents) c.update4Renderer(gl);
	}
	
	public void addGameComponent(Component component){
		gameComponents.add(component);
	}

	public void addRenderComponent(Component component){
		renderComponents.add(component);
	}
}
