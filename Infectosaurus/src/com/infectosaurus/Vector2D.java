package com.infectosaurus;

public class Vector2D {
	private float[] vector;
	
	Vector2D(float x, float y){
		vector = new float[2];
		vector[0] = x;
		vector[1] = y;
	}
	
	void setVector(float x, float y){
		vector[0] = x;
		vector[1] = y;
	}
	
	float[] getVector(){
		return vector;
	}
}
