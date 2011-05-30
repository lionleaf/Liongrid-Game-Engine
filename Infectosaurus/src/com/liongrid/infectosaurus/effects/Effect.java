package com.liongrid.infectosaurus.effects;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.GameObject;
import com.liongrid.infectosaurus.InfectoGameObject;

public abstract class Effect extends BaseObject{
	float duration = 0; // 0 means one-time effect
	
	
	public abstract void update(float dt, 
							    InfectoGameObject target, 
							    InfectoGameObject afflictor);
	
	public void update(float dt, BaseObject parent){
		update(dt, (InfectoGameObject) parent, null);
		duration -= dt;
	}
	public void set(float duration){
		this.duration = duration;
		
	}

	public boolean expired() {
		return duration < 0;
	}
}
