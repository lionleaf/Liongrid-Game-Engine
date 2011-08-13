package com.liongrid.infectosaurus;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCollision;
import com.liongrid.gameengine.LCollisionObject;
import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.tools.LVector2;
import com.liongrid.infectosaurus.components.SpriteComponent;

public class InfectoGameObject extends LGameObject<InfectoGameObject>{
	
	//TODO Don't have public!
	
	public Team team = Team.Human; //Default team
	public int mWidth = 0;
	public int mHeigth = 0;
	public boolean alive = true;
	public LVector2 mVel = new LVector2(0,0);
	public float speed = 10;
	public int mMaxHp = 1;
	public int mHp = mMaxHp;
	public boolean infectable = true; // Tells if the object can be infected
	public LCollisionObject collisionObject;
	public SpriteComponent spriteComponent = null;
	
	public InfectoGameObject() {
		
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
	public void addComponent(LComponent<InfectoGameObject> component) {
		super.addComponent(component);
		if(component instanceof SpriteComponent){
			spriteComponent = (SpriteComponent) component;
		}
	}
	
	@Override
	public void reset() {
		super.reset();
		spriteComponent = null;
	}
	
	protected void die(){
		GameActivity.infectoPointers.gameObjectHandler.remove(this);
	}

	public float distance2(InfectoGameObject o){
		return LCollision.distance2(collisionObject, o.collisionObject);
	}
}
