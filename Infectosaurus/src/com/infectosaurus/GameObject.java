package com.infectosaurus;

import android.util.Log;

import com.infectosaurus.components.Component;
import com.infectosaurus.effects.Effect;

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
	public int hp = 1;
	
	
	
	
	private FixedSizeArray<Component> components;
	private FixedSizeArray<Effect> effects;
	
	private static final int DEFAULT_COMPONENT_SIZE = 64;
	private static final int DEFAULT_EFFECT_SIZE = 64;
	
	private int Counter = 0;
	

	
	GameObject(){
		Log.d("GameBoard", "In BaseObject");
		components = new FixedSizeArray<Component>(DEFAULT_COMPONENT_SIZE);
		effects = new FixedSizeArray<Effect>(DEFAULT_COMPONENT_SIZE);
		Log.d("Place", "GameObject construct");
	}
	
	GameObject(int size){
		components = new FixedSizeArray<Component>(size);
	}
	
	private void die(){
		BaseObject.gamePointers.gameObjectHandler.remove(this);
	}
	
	@Override
	public void update(float dt, BaseObject parent){
		if(hp < 0) { // Temp death function!!! TODO RREMOVE
			die();
			return;
		}
		int size = effects.getCount();
		for (int i = 0; i < size; i++) {
			Effect e = effects.get(i);
			if(!e.expired()){
				e.update(dt, this);
			}else{
				effects.swapWithLast(i);
				effects.removeLast();
			}
		}
		
		size = components.getCount();
		while(Counter < size){
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
	
	public void afflict(Effect e){
		Log.d("GameObject", "AFFLICTED");
		effects.add(e);
	}

}
