package com.liongrid.gameengine;

import android.util.Log;

import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.Main;

/**
 * @author Lionleaf
 *
 */
public abstract class GameObject<T extends GameObject<?>> extends BaseObject{

	private FixedSizeArray<Component<T>> components;
	private FixedSizeArray<Effect<T>> effects;
	
	private static final int DEFAULT_COMPONENT_SIZE = 64;
	private static final int DEFAULT_EFFECT_SIZE = 64;
	
	private int Counter = 0;
	

	
	protected GameObject(){
		Log.d(Main.TAG, "In BaseObject");
		components = new FixedSizeArray<Component<T>>(DEFAULT_COMPONENT_SIZE);
		effects = new FixedSizeArray<Effect<T>>(DEFAULT_EFFECT_SIZE);
		Log.d(Main.TAG, "GameObject construct");
	}
	
	GameObject(int size){
		components = new FixedSizeArray<Component<T>>(size);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(float dt, BaseObject parent){
		
		int size = effects.getCount();
		Object[] rawArr = effects.getArray();
		for (int i = 0; i < size; i++) {
			Effect<T> e = (Effect<T>) rawArr[i];
			
			if(!e.expired()){
				if(e.firstTick()){
					e.onApply((T) this);
				}
				e.update(dt, this);
			}else{
				e.onRemove((T) this);
				effects.swapWithLast(i);
				effects.removeLast();
				//Since we swapped and removed, adjust counters
				size--;
				i--;
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
	
	/**
	 * This is potentially really slow, should be tested!
	 * 
	 * @param type The type your looking for. ie. SpriteComponent.class;
	 * @return a component of the type, or null if none was found
	 */
	public Component<T> findComponentOfType(Class<? extends Component<T>> type){
		Object[] rawArray = components.getArray();
		int size = components.getCount();
		for(int i = 0; i < size; i++){
			if(type.isAssignableFrom(rawArray[i].getClass())){
				return (Component<T>) rawArray[i];
			}
		}
		return null;
	}
	
	@Override
	public void reset(){
		
	}
	
	/**
	 * Adds an Effect to the effect array. When the GameObject is updated, each
	 * of the effects will be updated also.
	 * @param e - The effect to be added to the effect list. The effects needs to
	 * extend GameEngine.Effect
	 */
	public void afflict(Effect e){
		effects.add(e);
	}

}
