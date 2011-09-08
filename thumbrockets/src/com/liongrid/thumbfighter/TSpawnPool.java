package com.liongrid.thumbfighter;

import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.components.LDrawableComponent;
import com.liongrid.gameengine.components.LMoveComponent;
import com.liongrid.thumbfighter.components.TRemoveOutsideComponent;

public class TSpawnPool {
	int spriteHeight = 64;
	public TGameObject spawnRocket(int speed, float posX, TPlayerID player){
		TGameObject rocket = new TGameObject();
		int res = (player == TPlayerID.player1) ? R.drawable.redrocket : R.drawable.greenrocket;
		LDrawableBitmap bitmap = new LDrawableBitmap(LGamePointers.textureLib.allocateTexture(res), 32, 128);
		
		bitmap.setFlip(false, (player == TPlayerID.player2));
		rocket.addComponent(new LDrawableComponent(bitmap)); 
		
		
		rocket.addComponent(new LMoveComponent());
		rocket.addComponent(new TRemoveOutsideComponent());
		rocket.speed = speed;
		rocket.vel.y = (player == TPlayerID.player1)? speed : -speed;
		rocket.pos.x = posX;
		rocket.pos.y = (player == TPlayerID.player1) ? -spriteHeight : LGamePointers.panel.getHeight();
		return rocket; 
	}
}
