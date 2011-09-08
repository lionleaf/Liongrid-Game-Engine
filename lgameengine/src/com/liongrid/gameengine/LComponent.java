package com.liongrid.gameengine;


public abstract class LComponent<T extends LGameObject<T>> extends LBaseObject{
	
	public LComponent() {
		super();
	}
	
	public abstract void update(float dt, T parent);
	
	//Made final to force subclasses to fill in the correct update method
	@Override
	public final void update(float dt, LBaseObject parent){
		throw new RuntimeException("You called an update method in component not " +
				"meant to be called. Call the generic one instead!");
	};
	
	@Override
	public void reset(){
		//TODO
	}
}
