package com.liongrid.infectosaurus.crowd.situations;

import com.liongrid.gameengine.GameObject;

public abstract class Situation {
	Situation(GameObject go) {
		applySituation(go);
	}

	/**
	 * The method can put in and out components, add and remove components
	 * and add or remove states. 
	 * @param go GameObject the situation should apply its modifiers to
	 */
	abstract void applySituation(GameObject go);
		
}
