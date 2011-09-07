package com.liongrid.infectomancer;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCollision;
import com.liongrid.gameengine.LCollisionObject;
import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.components.LSpriteComponent;
import com.liongrid.gameengine.tools.LVector2;

public class IGameObject extends LGameObject<IGameObject>{
	
	//TODO Don't have public!
	
	public ITeam team = ITeam.Human; //Default team
	
	public boolean alive = true;
	
	public int mMaxHp = 1;
	public int mHp = mMaxHp;
	public boolean infectable = true; // Tells if the object can be infected
	public LCollisionObject collisionObject;
	public LSpriteComponent spriteComponent = null;
	
	public IGameObject() {
		
	}
	
	
	@Override
	public void update(float dt, LBaseObject parent) {
		if(mHp <= 0) { // Temp death function!!! TODO RREMOVE
			die();
			return;
		}
		super.update(dt, parent);
	}
	
	@Override
	public void addComponent(LComponent component) {
		super.addComponent(component);
		if(component instanceof LSpriteComponent){
			spriteComponent = (LSpriteComponent) component;
		}
	}
	
	@Override
	public void reset() {
		super.reset();
		spriteComponent = null;
	}
	
	protected void die(){
		IGamePointers.gameObjectHandler.remove(this);
	}

	public float distance2(IGameObject o){
		return LCollision.distance2(collisionObject, o.collisionObject);
	}
}
