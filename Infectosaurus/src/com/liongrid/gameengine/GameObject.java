package com.liongrid.gameengine;

import android.util.Log;

import com.liongrid.infectosaurus.Main;
import com.liongrid.infectosaurus.effects.Effect;
import com.liongrid.infectosaurus.tools.FixedSizeArray;
import com.liongrid.infectosaurus.tools.Vector2;

/**
 * @author Lionleaf
 *
 */
public abstract class GameObject<T extends GameObject<?>> extends BaseObject{

	private FixedSizeArray<Component<T>> components;
	private FixedSizeArray<Effect> effects;
	
	private static final int DEFAULT_COMPONENT_SIZE = 64;
	private static final int DEFAULT_EFFECT_SIZE = 64;
	
	private int Counter = 0;
	

	
	protected GameObject(){
		Log.d(Main.TAG, "In BaseObject");
		components = new FixedSizeArray<Component<T>>(DEFAULT_COMPONENT_SIZE);
		effects = new FixedSizeArray<Effect>(DEFAULT_EFFECT_SIZE);
		Log.d(Main.TAG, "GameObject construct");
	}
	
	GameObject(int size){
		components = new FixedSizeArray<Component<T>>(size);
	}

	@Override
	public void update(float dt, BaseObject parent){
		
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
			components.get(Counter++).update(dt, (T) this);
		}
		Counter = 0;
	}
	
	
	/**
	 * Adds a component that will be updated every time this object is
	 * @param component - The component object
	 */
	public void addComponent(Component<T> component){
		components.add(component);
	}
	
	@Override
	public void reset(){
		
	}
	
	public void afflict(Effect e){
		effects.add(e);
	}

}
