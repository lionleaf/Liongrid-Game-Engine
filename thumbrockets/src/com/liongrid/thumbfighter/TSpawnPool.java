package com.liongrid.thumbfighter;

import com.liongrid.gameengine.LCollisionNonRotateSquare;
import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.components.LDrawableComponent;
import com.liongrid.gameengine.components.LMoveComponent;
import com.liongrid.thumbfighter.components.TExplosiveCollisionComponent;
import com.liongrid.thumbfighter.components.TRemoveOutsideComponent;

public class TSpawnPool {
	
	public TGameObject spawnRocket(int speed, float posX, TPlayerID player){
		
		int width = 32;
		int height = 128;
		
		TGameObject rocket = new TGameObject();
		int res = (player == TPlayerID.player1) ? R.drawable.redrocket : R.drawable.greenrocket;
		LDrawableBitmap bitmap = new LDrawableBitmap(LGamePointers.textureLib.
				allocateTexture(res), width, height);
		
		bitmap.setFlip(false, (player == TPlayerID.player2));
		rocket.addComponent(new LDrawableComponent(bitmap)); 
		
		
		rocket.addComponent(new LMoveComponent());
		rocket.addComponent(new TRemoveOutsideComponent());
		rocket.addComponent(new TExplosiveCollisionComponent());
		rocket.speed = speed;
		rocket.vel.y = (player == TPlayerID.player1)? speed : -speed;
		rocket.pos.x = posX;
		rocket.pos.y = (player == TPlayerID.player1) ? -height : LGamePointers.panel.getHeight();
		
		rocket.hitBox = new LCollisionNonRotateSquare(player.ordinal(),rocket,width, height);		
		return rocket; 
	}
}
