package com.liongrid.infectosaurus.components;

import com.liongrid.gameengine.LCollisionObject;
import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LShape;
import com.liongrid.gameengine.tools.LVector2;
import com.liongrid.infectosaurus.IGameObject;

/**
 * @author Lastis
 *	For this component to work, the IGameObject needs to have a collision object with
 *	a shape. This component moves the parent away from any InfectoGameObjects it collides with.
 */
public class ICollisionComponent extends LComponent<IGameObject>{

	@Override
	public void update(float dt, IGameObject parent) {
		if(parent.collisionObject == null) return;
		LCollisionObject collisionObject = parent.collisionObject;
		for(int i = 0; i < collisionObject.collisionCnt ; i++){
			moveObjectAway(collisionObject, collisionObject.collisions[i]);
		}
	}
	
	private void moveObjectAway(LCollisionObject shape1, LCollisionObject shape2){
		if(shape1.getShape() == LShape.CIRCLE && 
			shape2.getShape() == LShape.CIRCLE){
			
			LShape.Circle circle1 = (LShape.Circle) shape1;
			LShape.Circle circle2 = (LShape.Circle) shape2;
			
			LVector2 pos1 = circle1.getPos();
			LVector2 pos2 = circle2.getPos();
			float radius1 = circle1.getRadius();
			float radius2 = circle2.getRadius();
			
			float[] AB = {pos1.x - pos2.x, pos1.y - pos2.y};
			float absAB = (float) Math.sqrt(AB[0] * AB[0] + AB[1] * AB[1]);
			
			float cosPhi;
			float sinPhi;
			if(absAB != 0){
				cosPhi = AB[0] / absAB;
				sinPhi = AB[1] / absAB;
			}else{ // If it`s zero, just teleport to the side instead of getting NaN
				cosPhi = 1;
				sinPhi = 0;
			}
			
			pos1.x = pos2.x + cosPhi * radius1 + cosPhi * radius2;
			pos1.y = pos2.y + sinPhi * radius1 + sinPhi * radius2;
		}
	}
}
