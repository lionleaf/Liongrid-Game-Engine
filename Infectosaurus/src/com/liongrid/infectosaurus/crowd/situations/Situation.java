package com.liongrid.infectosaurus.crowd.situations;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.GameObject;

public abstract class Situation extends BaseObject{
	/**
	 * Tells if the situation is tied to the environment or not.
	 */
	public boolean spatial;
	Situation() {
		
	}

	/**
	 * The method can put in and out components, add and remove components
	 * and add or remove states. 
	 * @param go GameObject the situation should apply its modifiers to
	 */
	abstract void apply(GameObject go);
	abstract void remove(GameObject go);
}
