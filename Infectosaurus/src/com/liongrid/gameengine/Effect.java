package com.liongrid.gameengine;

import com.liongrid.infectosaurus.InfectoGameObject;

/**
 * @author Liongrid
 *	
 * This represents an effect that does something to an object. 
 * 
 * To make a new effect, extend this class and override the abstract methods.
 * Do not override update() (Or if you do, call super.update()).
 * 
 * To implement an effect system, you have to have a list of the effects,
 * and each frame check whether the effect has expired or not; if it has not,
 * call update(dt, target), if it has, call onRemove(target) 
 * and remove it from the list 
 * 
 * @param <T> - The type of object that this effect can be applied to.
 */
public abstract class Effect<T extends BaseObject> extends BaseObject{
	float duration = 0; // 0 means one-time effect
	private boolean ticked = false;
	
	/**
	 * This will be called every frame while the effect is active.
	 * Remember that the framerate is not static, so you have to use dt.
	 * 
	 * @param dt - Time in sec since last tick.
	 * @param target - The object that has the effect on itself
	 */
	public abstract void tick(float dt, T target);
	
	
	/**
	 * This method is called before the first tick of the effect.
	 * (But in the same frame)
	 * @param target - The object that has the effect on itself
	 */
	public abstract void onApply(T target);
	
	
	/**
	 * This method is called the frame after the last tick, 
	 * right before it is removed.
	 * @param target - The object that has the effect on itself
	 */
	public abstract void onRemove(T target);

	
	@SuppressWarnings("unchecked")
	@Override
	public void update(float dt, BaseObject parent){
		
		assert duration >= 0;
		
		if(firstTick()){
			onApply((T) parent);
		}
		tick(dt, (T) parent);
		ticked = true;
		duration -= dt;
	}
	
	/**
	 * Sets all the parameters of the effect. 
	 * (It will be pooled, so the constructor must be empty, and the effect reused)
	 * Overrides should call super.set
	 * @param duration
	 */
	public void set(float duration){
		this.duration = duration;	
	}

	/**
	 * @return - true if the effect has expired.
	 * 
	 * If the effect is expired, call onRemove and then remove the effect 
	 * (return it to the pool)
	 */
	public boolean expired() {
		return duration < 0;
	}

	
	
	
	public boolean firstTick(){
		return !ticked ;
	}
	
	@Override
	public void reset() {
		duration = 0;
		ticked = false;
		
	}
}
