package com.liongrid.infectosaurus.crowd.situations;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.components.BehaviorComponent;

public abstract class Situation extends LBaseObject{
	Situation() {
		
	}

	/**
	 * The method can put in and out components, add and remove components
	 * and add or remove states. 
	 * @param go LGameObject the situation should apply its modifiers to
	 */
	abstract void apply(InfectoGameObject go, BehaviorComponent behaveComp);
	abstract void remove(InfectoGameObject go, BehaviorComponent behaveComp);
}
