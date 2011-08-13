package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.Vector2;

public class CollisionCircle extends CollisionObject implements Shape.Circle{
	
	public float radius;

	public CollisionCircle(int type, int maxCollisions, 
			GameObject owner, float radius) {
		super(type, owner, maxCollisions);
		this.radius = radius;
	}
	
	public CollisionCircle(int type, GameObject owner, float radius) {
		super(type, owner);
		this.radius = radius;
	}

	public int getShape() {
		return Shape.CIRCLE;
	}

	public float getRadius() {
		return radius;
	}

}
