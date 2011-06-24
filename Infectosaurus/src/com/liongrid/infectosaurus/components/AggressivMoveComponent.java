package com.liongrid.infectosaurus.components;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.GameObject;
import com.liongrid.infectosaurus.GameActivity;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.InfectoGameObjectHandler;
import com.liongrid.infectosaurus.Main;
import com.liongrid.infectosaurus.Team;

public class AggressivMoveComponent extends Component<InfectoGameObject>{
	
	private InfectoGameObjectHandler gameObjHandler;

	public AggressivMoveComponent(){
		gameObjHandler = GameActivity.infectoPointers.gameObjectHandler;
	}

	@Override
	public void update(float dt, InfectoGameObject parent) {
		if(parent.collideCnt != 0){
			for (int i = 0; i < parent.collideCnt; i++) {
				if(parent.collisions[i].team == Team.Alien) return; 
			}
		}
		InfectoGameObject target =
			//gameObjHandler.getClosest(parent, parent.pos, Team.Human);
			gameObjHandler.ca.getClosest(parent.pos, Team.Human.ordinal());
		if(target != null){
			parent.vel.set(target.pos);
			parent.vel.subtract(parent.pos);
			parent.vel.normalize();
			parent.vel.multiply(parent.speed);
		}
	}
}
