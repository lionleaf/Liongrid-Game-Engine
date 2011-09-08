package com.liongrid.gameengine;


/**
 * 
 * The abstract class from which all components should inherit. 
 * 
 * @author Lionleaf
 *
 * @param <T> - The type of GameObject this component is made for. Notice that 
 * the parent paramater in the update method passes a T argument. This way, you
 * won`t have to cast.
 */
public abstract class LComponent<T extends LGameObject<?>> extends LBaseObject{
	
	public LComponent() {
		super();
	}
	
	public abstract void update(float dt, T parent);
	
	//Made final to force subclasses to fill in the correct update method
	@Override
	public final void update(float dt, LBaseObject parent){
		throw new RuntimeException("You called an update method in component not " +
				"meant to be called. Call the generic one instead!");
	};
	
	@Override
	public void reset(){
		//TODO
	}
}
