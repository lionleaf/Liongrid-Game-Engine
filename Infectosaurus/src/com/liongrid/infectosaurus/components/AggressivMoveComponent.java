package com.liongrid.infectosaurus.components;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.GameObject;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.InfectoGameObjectHandler;

public class AggressivMoveComponent extends Component<InfectoGameObject>{
	
	private InfectoGameObjectHandler gameObjHandler;

	public AggressivMoveComponent(){
		gameObjHandler = GameActivity.infectoPointers.gameObjectHandler;
	}

	@Override
	public void update(float dt, InfectoGameObject parent) {
		InfectoGameObject target = gameObjHandler.getClosest(parent, parent.team.Human);
		if(target != null){
			parent.vel.set(target.pos);
			parent.vel.subtract(parent.pos);
			parent.vel.normalize();
			parent.vel.multiply(parent.speed);
		}
	}
}
