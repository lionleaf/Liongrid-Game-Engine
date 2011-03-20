package com.infectosaurus;

public abstract class BaseObject {
	FixedSizeArray<Component> gameComponents;
	FixedSizeArray<Component> renderComponents;
	
	public void useComp4Game(){
		
	}
	
	public void useComp4Renderer(){
		
	}
	
	public void addGameComponent(Component c){
		gameComponents.add(c);
	}

	public void addRenderComponent(Component c){
		renderComponents.add(c);
	}
}
