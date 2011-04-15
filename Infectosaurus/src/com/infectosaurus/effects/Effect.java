package com.infectosaurus.effects;

import com.infectosaurus.BaseObject;
import com.infectosaurus.GameObject;

public abstract class Effect extends BaseObject{
	float duration = 0; // 0 means one-time effect
	
	
	public abstract void update(float dt, 
							    GameObject target, 
							    GameObject afflictor);
	
	public void update(float dt, BaseObject parent){
		update(dt, (GameObject) parent, null);
		duration -= dt;
	}
	public void set(float duration){
		this.duration = duration;
		
	}

	public boolean expired() {
		return duration < 0;
	}
}