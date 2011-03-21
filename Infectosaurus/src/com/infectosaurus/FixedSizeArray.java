package com.infectosaurus;

public class FixedSizeArray<T> {
	private static final int DEFAULT_SIZE = 10;
	private final T[] array;
	
	FixedSizeArray(){
		array = (T[])new Object[DEFAULT_SIZE];
	}
	FixedSizeArray(int size){
		array = (T[])new Object[size];
	}

	public void add(T t) {
		
	}

}
