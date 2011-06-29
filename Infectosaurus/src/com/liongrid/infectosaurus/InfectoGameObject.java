package com.liongrid.infectosaurus;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Collision;
import com.liongrid.gameengine.Shape;
import com.liongrid.gameengine.GameObject;
import com.liongrid.gameengine.Shape.CHCircle;
import com.liongrid.gameengine.tools.FixedSizeArray;
import com.liongrid.gameengine.tools.Vector2;

public class InfectoGameObject extends GameObject<InfectoGameObject> 
		implements CHCircle<InfectoGameObject>{

	
	private static final int COLLISION_ARR_LENGTH = 5;
	public Team team = Team.Human; //Default team
	public boolean alive = true;
	public Vector2 pos = new Vector2(0,0);
	public Vector2 vel = new Vector2(0,0);
	public float speed = 10;
	public int mMaxHp = 1;
	public int mHp = mMaxHp;
	public boolean infectable = true; // Tells if the object can be infected
	public InfectoGameObject[] collisions;
	public int collideCnt = 0;
	public float radius = 0;
	
	
	public InfectoGameObject() {
		collisions = new InfectoGameObject[COLLISION_ARR_LENGTH];
	}
	
	
	@Override
	public void update(float dt, BaseObject parent) {
		if(mHp <= 0) { // Temp death function!!! TODO RREMOVE
			die();
			return;
		}
		super.update(dt, parent);
	}
	
	protected void die(){
		GameActivity.infectoPointers.gameObjectHandler.remove(this);
	}

	public void collide(InfectoGameObject o) {
		if(collideCnt >= collisions.length) return;
		if(Collision.collides(this, o)){
			collisions[collideCnt] = o;
			collideCnt++;
		}
	}

	public void clear() {
		collideCnt = 0;
		for(int i = 0; i < collisions.length; i++){
			collisions[i] = null;
		}
	}

	public int getType() {
		return team.ordinal();
	}

	public Vector2 getPos() {
		return pos;
	}

	public float getRadius() {
		return radius;
	}

	public int getShape() {
		return Shape.CIRCLE;
	}
}
