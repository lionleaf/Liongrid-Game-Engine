package com.liongrid.infectosaurus;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Collision;
import com.liongrid.gameengine.CollisionObject;
import com.liongrid.gameengine.GameObject;
import com.liongrid.gameengine.tools.Vector2;

public class InfectoGameObject extends GameObject<InfectoGameObject>{
	
	public Team team = Team.Human; //Default team
	public int width = 0;
	public int heigth = 0;
	public boolean alive = true;
	public Vector2 vel = new Vector2(0,0);
	public float speed = 10;
	public int mMaxHp = 1;
	public int mHp = mMaxHp;
	public boolean infectable = true; // Tells if the object can be infected
	public CollisionObject collisionObject;
	
	public InfectoGameObject() {
		
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

	public float distance2(InfectoGameObject o){
		return Collision.distance2(collisionObject, o.collisionObject);
	}
}
