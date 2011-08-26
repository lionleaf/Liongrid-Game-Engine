package com.liongrid.infectomancer.crowd.situations;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.infectomancer.IGameObject;
import com.liongrid.infectomancer.components.IBehaviorComponent;

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
