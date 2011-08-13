package com.liongrid.gameengine;

import android.util.Log;

import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.gameengine.tools.LVector2;
import com.liongrid.infectosaurus.Main;

/**
 * @author Lionleaf
 * 
 */
public abstract class LGameObject<T extends LGameObject<?>> extends LBaseObject {

	public LVector2 pos = new LVector2(0,0);
	
	private LFixedSizeArray<LComponent<T>> components;
	private LFixedSizeArray<LEffect<T>> effects;

	private static final int DEFAULT_COMPONENT_SIZE = 64;
	private static final int DEFAULT_EFFECT_SIZE = 64;

	private int Counter = 0;

	protected LGameObject() {
		Log.d(Main.TAG, "In LBaseObject");
		components = new LFixedSizeArray<LComponent<T>>(DEFAULT_COMPONENT_SIZE);
		effects = new LFixedSizeArray<LEffect<T>>(DEFAULT_EFFECT_SIZE);
		Log.d(Main.TAG, "LGameObject construct");
	}

	LGameObject(int size) {
		components = new LFixedSizeArray<LComponent<T>>(size);
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
	public void addComponent(LComponent<T> component) {
		components.add(component);
	}

	/**
	 * This is potentially really slow, should be tested!
	 * 
	 * @param type
	 *            The type your looking for. ie. SpriteComponent.class;
	 * @return a component of the type, or null if none was found
	 */
	public LComponent<T> findComponentOfType(Class<? extends LComponent<T>> type) {
		Object[] rawArray = components.getArray();
		int size = components.getCount();
		for (int i = 0; i < size; i++) {
			if (type.isAssignableFrom(rawArray[i].getClass())) {
				return (LComponent<T>) rawArray[i];
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
	public void afflict(LEffect e) {
		effects.add(e);
	}

}
