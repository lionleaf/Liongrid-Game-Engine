package com.liongrid.gameengine;

import com.liongrid.infectosaurus.InfectoGameObject;

public abstract class Effect<T extends BaseObject> extends BaseObject{
	float duration = 0; // 0 means one-time effect
	private boolean ticked = false;
	
		
	/**
	 * You must call super when you override this!
	 * @param dt - time since last call in sek
	 * @param parent - The object to apply stuff to
	 * @return 
	 */
	public abstract void tick(float dt, T parent);
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(float dt, BaseObject parent){
		
		assert duration >= 0;
		tick(dt, (T) parent);
		ticked = true;
		duration -= dt;
	}
	
	public void set(float duration){
		this.duration = duration;
		
	}

	public boolean expired() {
		return duration < 0;
	}

	public abstract void onApply(T target);
	
	public abstract void onRemove(T target);
	
	public boolean firstTick(){
		return !ticked ;
	}
	
	@Override
	public void reset() {
		duration = 0;
		ticked = false;
		
	}
}
