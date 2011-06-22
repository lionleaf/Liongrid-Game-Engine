package com.liongrid.infectosaurus;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Shape;
import com.liongrid.gameengine.GameObject;
import com.liongrid.gameengine.Shape.CHCircle;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.gameengine.tools.Vector2;

public class InfectoGameObject extends GameObject<InfectoGameObject> 
		implements CHCircle<InfectoGameObject>{

	
	public Team team = Team.Human; //Default team
	public boolean alive = true;
	public Vector2 pos = new Vector2(0,0);
	public Vector2 vel = new Vector2(0,0);
	public float speed = 10;
	public int hp = 1;
	public boolean infectable = true; // Tells if the object can be infected
	
	protected float hitboxR = 0;
	protected float radius = 0;
	protected FixedSizeArray<InfectoGameObject> collisions;
	
	
	
	@Override
	public void update(float dt, BaseObject parent) {
		if(hp <= 0) { // Temp death function!!! TODO RREMOVE
			die();
			return;
		}
		super.update(dt, parent);
	}
	
	protected void die(){
		GameActivity.infectoPointers.gameObjectHandler.remove(this);
	}

	public void collide(InfectoGameObject o) {
		
	}

	public void clear() {
		
	}

	public int[] getPossibleCollisions() {
		return null;
	}

	public int[] getType() {
		int[] i = {team.ordinal()};
		return i;
	}

	public Vector2 getPos() {
		return pos;
	}

	public float getRadius() {
		return hitboxR;
	}

	public int getShape() {
		return Shape.CIRCLE;
	}

	public void expandHitbox(float dt) {
		hitboxR = radius*speed*dt;
	}

	public void resetHitbox() {
		hitboxR = radius;
	}
}
