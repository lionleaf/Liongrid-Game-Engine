package com.infectosaurus;

import android.util.Log;

import com.infectosaurus.components.Component;

public abstract class GameObject extends BaseObject{
	public float posX = 100;
	public float posY = 100;
	
	public float velX;
	public float velY;
	
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
			components.get(Counter++).update(0, this);
		}
		Counter = 0;
	}
	
	
	public void addComponent(Component component){
		components.add(component);
	}
	
	@Override
	public void reset(){
		//TODO!
	}

}
