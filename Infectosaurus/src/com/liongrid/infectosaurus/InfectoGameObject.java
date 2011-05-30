package com.liongrid.infectosaurus;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.GameObject;
import com.liongrid.infectosaurus.tools.Vector2;

public class InfectoGameObject extends GameObject<InfectoGameObject> {

	public Team team = Team.Human; //Default team
	public boolean alive = true;
	public Vector2 pos = new Vector2(0,0);
	public Vector2 vel = new Vector2(0,0);
	public float speed = 10;
	public int hp = 1;
	
	
	
	@Override
	public void update(float dt, BaseObject parent) {
		// TODO Auto-generated method stub
		if(hp < 0) { // Temp death function!!! TODO RREMOVE
			die();
			return;
		}
		super.update(dt, parent);
	}
	
	protected void die(){
		BaseObject.gamePointers.gameObjectHandler.remove(this);
	}
}
