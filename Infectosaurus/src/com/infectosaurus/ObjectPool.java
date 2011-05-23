package com.infectosaurus;

import android.util.Log;

/**
 * @author Lionleaf
 * 
 * A pool of objects, used to avoid allocations while the game is running
 *
 * @param <E> The type of pool. Must extend BaseObject 
 * and have an empty constructor available!
 */
public class ObjectPool<E extends BaseObject> {
	FixedSizeArray<E> objects;
	Class<E> objectClass;

	/**
	 * You pass the Class object like this:
	 * new ObjectPool<MyClass>(500, MyClass.class);
	 * @param size - The size of the pool. Make it big enough never to be exhausted!
	 * @param oClass - The Class object for the type.
	 */
	public ObjectPool(int size, Class<E> oClass) {
		objects = new FixedSizeArray<E>(size);
		objectClass = oClass;
		constructObjects();

	}

	private void constructObjects() {
		int size = objects.getCapacity();
		for (int i = 0; i < size; i++) {
			try {
				objects.add(objectClass.newInstance());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Fetch an object you want to use from the pool.
	 * Remember to release() it when you're done with it.
	 * We don't want any leaks!
	 * @return the object that can be used.
	 */
	public E allocate(){
		if(objects.getCount() == 0){
			Log.e(Main.TAG, 
					"EXHAUSTED "+objectClass.getSimpleName()+ " pool! "+objects.getCapacity());
			return null;
		}
		
		return objects.removeLast();
	}
	
	/**
	 * Calls reset() on the object and puts it back in the pool.
	 * Do not keep any pointers to this object! 
	 * @param object The object to be released back to the pool.
	 */
	public void release(E object){
		object.reset();
		objects.add(object);
	}
	
}
