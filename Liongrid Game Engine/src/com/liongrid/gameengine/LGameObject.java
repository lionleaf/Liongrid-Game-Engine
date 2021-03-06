package com.liongrid.gameengine;

import android.util.Log;

import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.gameengine.tools.LVector2;


/**
 * 
 *	
 * This is the basic game unit. Every game must override it and pass itself
 * as the T parameter. This is to make sure we send the right type of GameObject to 
 * all the components. This way, we don`t need unsafe casts.
 *
 * @author Liongrid
 * @param <T> Every overriding class should pass itself as T. ie. 
 * 	<br>class IGameObject extends LGameObject&ltIGameObject&gt
 * 
 */
public abstract class LGameObject<T extends LGameObject<T>> extends LBaseObject {

	public LVector2 pos = new LVector2(0,0);
	public int width = 0;
	public int heigth = 0;
	public LVector2 vel = new LVector2(0,0);
	public float speed = 0;

	
	private LFixedSizeArray<LComponent<LGameObject<?>>> components;
	private LFixedSizeArray<LEffect<T>> effects;

	private static final int DEFAULT_COMPONENT_SIZE = 64;
	private static final int DEFAULT_EFFECT_SIZE = 64;

	private int Counter = 0;

	public LGameObject() {
		init(DEFAULT_COMPONENT_SIZE, DEFAULT_EFFECT_SIZE);
	}

	LGameObject(int compSize, int effSize) {
		init(compSize, effSize);
	}

	private void init(int compSize, int effSize){
		Log.v(LConst.TAG, "In LBaseObject");
		components = new LFixedSizeArray<LComponent<LGameObject<?>>>(compSize);
		effects = new LFixedSizeArray<LEffect<T>>(effSize);
	}
	@SuppressWarnings("unchecked")
	@Override
	public void update(float dt, LBaseObject parent) {

		int size = effects.getCount();
		Object[] rawArr = effects.getArray();
		for (int i = 0; i < size; i++) {
			LEffect<T> e = (LEffect<T>) rawArr[i];

			if (!e.expired()) {
				e.update(dt, this);
			} else {
				e.onRemove((T) this);
				effects.swapWithLast(i);
				effects.removeLast();
				// Since we swapped and removed, adjust counters
				size--;
				i--;
			}
		}

		size = components.getCount();
		while (Counter < size) {
			components.get(Counter++).update(dt, (T) this);
		}
		Counter = 0;
	}

	/**
	 * Adds a component that will be updated every time this object is
	 * 
	 * @param component
	 *            - The component object
	 */
	public void addComponent(LComponent component) {
		components.add(component);
	}

	/**
	 * This is potentially really slow, should be tested!
	 * 
	 * @param type
	 *            The type your looking for. ie. ISpriteComponent.class;
	 * @return a component of the type, or null if none was found
	 */
	public LComponent findComponentOfType(Class<? extends LComponent> type) {
		Object[] rawArray = components.getArray();
		int size = components.getCount();
		for (int i = 0; i < size; i++) {
			if (type.isAssignableFrom(rawArray[i].getClass())) {
				return (LComponent) rawArray[i];
			}
		}
		return null;
	}

	@Override
	public void reset() {

	}

	/**
	 * Adds an LEffect to the effect array. When the LGameObject is updated, each
	 * of the effects will be updated also.
	 * 
	 * @param e
	 *            - The effect to be added to the effect list. The effects needs
	 *            to extend GameEngine.Effect
	 */
	public void afflict(LEffect<T> e) {
		effects.add(e);
	}

}
