package com.infectosaurus;

import android.util.Log;

import com.infectosaurus.components.Component;

/**
 * @author Lionleaf
 *
 */
public abstract class GameObject extends BaseObject{
	
	enum Team{ Human, Alien };
	
	public Team team = Team.Human; //Default team
	
	public boolean alive = true;
	
	public Vector2 pos = new Vector2(0,0);
	
	public Vector2 vel = new Vector2(0,0);
	
	public float speed = 10;
	
	private static final int DEFAULT_SIZE = 64;
	private FixedSizeArray<Component> components;
	private int Size;
	private int Counter = 0;

	
	GameObject(){
		Log.d("GameBoard", "In BaseObject");
		components = new FixedSizeArray<Component>(DEFAULT_SIZE);
		Log.d("Place", "GameObject construct");
	}
	
	GameObject(int size){
		components = new FixedSizeArray<Component>(size);
	}
	
	@Override
	public void update(float dt, BaseObject parent){
		Size = components.getCount();
		while(Counter < Size){
			components.get(Counter++).update(dt, this);
		}
		Counter = 0;
	}
	
	
	/**
	 * Adds a component that will be updated every time this object is
	 * @param component - The component object
	 */
	public void addComponent(Component component){
		components.add(component);
	}
	
	@Override
	public void reset(){
		
	}

}
