package com.liongrid.infectosaurus.crowd.situations;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.infectosaurus.IGameObject;
import com.liongrid.infectosaurus.components.IBehaviorComponent;

public abstract class ISituation extends LBaseObject{
	ISituation() {
		
	}

	/**
	 * The method can put in and out components, add and remove components
	 * and add or remove states. 
	 * @param go LGameObject the situation should apply its modifiers to
	 */
	abstract void apply(IGameObject go, IBehaviorComponent behaveComp);
	abstract void remove(IGameObject go, IBehaviorComponent behaveComp);
}
