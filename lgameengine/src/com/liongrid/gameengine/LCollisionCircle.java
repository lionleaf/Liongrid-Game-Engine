package com.liongrid.gameengine;

public class LCollisionCircle extends LCollisionObject implements LShape.Circle{
	
	public float radius;

	public LCollisionCircle(int type, int maxCollisions, 
			LGameObject<?> owner, float radius) {
		super(type, owner, maxCollisions);
		this.radius = radius;
	}
	
	public LCollisionCircle(int type, LGameObject<?> owner, float radius) {
		super(type, owner);
		this.radius = radius;
	}

	public int getShape() {
		return LShape.CIRCLE;
	}

	public float getRadius() {
		return radius;
	}

}
