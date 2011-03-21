package com.infectosaurus;

import javax.microedition.khronos.opengles.GL10;

import com.infectosaurus.components.Component;

public abstract class BaseObject {
	FixedSizeArray<Component> gameComponents;
	FixedSizeArray<Component> renderComponents;
	BaseObject(){
		gameComponents = new FixedSizeArray<Component>();
		renderComponents = new FixedSizeArray<Component>();
	}
	
	BaseObject(int size){
		gameComponents = new FixedSizeArray<Component>(size);
		renderComponents = new FixedSizeArray<Component>(size);
	}
	
	public void useComp4Game(){
		
	}
	
	public void useComp4Renderer(GL10 gl){
		
	}
	
	public void addGameComponent(Component c){
		gameComponents.add(c);
	}

	public void addRenderComponent(Component c){
		renderComponents.add(c);
	}
}