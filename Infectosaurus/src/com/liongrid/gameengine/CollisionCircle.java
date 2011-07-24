package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.Vector2;

public class CollisionCircle extends CollisionObject implements Shape.Circle{
	
	public float radius;

	public CollisionCircle(int type, Vector2 pos, int maxCollisions, 
			Object owner, float radius) {
		super(type, pos, owner, maxCollisions);
		this.radius = radius;
	}
	
	public CollisionCircle(int type, Vector2 pos, Object owner, float radius) {
		super(type, pos, owner);
		this.radius = radius;
	}

	public int getShape() {
		return Shape.CIRCLE;
	}

	public float getRadius() {
		return radius;
	}

}
