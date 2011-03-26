package com.infectosaurus;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

import com.infectosaurus.components.Component;

public abstract class BaseObject {
	private static final int DEFAULT_SIZE = 10;
	private ArrayList<Component> gameComponents;
	private ArrayList<Component> renderComponents;
	private int GSize;
	private int GCounter = 0;
	private int RCounter;
	private int RSize;
	
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
		GSize = gameComponents.size();
		while(GCounter < GSize){
			gameComponents.get(GCounter++).update4Game(0);
		}
		GCounter = 0;
		
	}
	
	public void useComp4Renderer(GL10 gl){
		RSize = renderComponents.size();
		while(RCounter < RSize){
			renderComponents.get(RCounter++).update4Renderer(gl);
		}
		RCounter = 0;
	}
	
	public void addGameComponent(Component component){
		gameComponents.add(component);
	}

	public void addRenderComponent(Component component){
		renderComponents.add(component);
	}
}
