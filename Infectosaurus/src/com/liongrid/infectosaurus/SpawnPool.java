package com.liongrid.infectosaurus;

import java.util.Random;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.CollisionCircle;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.Texture;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.infectosaurus.components.AggressivMoveComponent;
import com.liongrid.infectosaurus.components.BehaviorComponent;
import com.liongrid.infectosaurus.components.CollisionComponent;
import com.liongrid.infectosaurus.components.HpBarComponent;
import com.liongrid.infectosaurus.components.InfMeleeAttackComponent;
import com.liongrid.infectosaurus.components.LAnimation;
import com.liongrid.infectosaurus.components.MoveComponent;
import com.liongrid.infectosaurus.components.SpriteComponent;
import com.liongrid.infectosaurus.components.SpriteComponent.SpriteState;
import com.liongrid.infectosaurus.effects.DOTEffect;

public class SpawnPool extends BaseObject{
	
	public InfectoGameObject spawnInfectosaurus(){
		InfectoGameObject object = new InfectoGameObject();
		return object;
	}
	
	public InfectoGameObject spawnMinion(){
		InfectoGameObject object = new InfectoGameObject();
		return object;
	}
	
	/**
	 * @param posX -1 for random
	 * @param posY -1 for random
	 * @param hp
	 * @return
	 */
	public static InfectoGameObject spawnInfectosaurus(float posX, float posY, int hp){
		
		InfectoGameObject object = new Infectosaurus();
		
		return object;
	}
	
	/**
	 * @param posX -1 for random
	 * @param posY -1 for random
	 * @param hp
	 * @return
	 */
	public static InfectoGameObject spawnHuman(float posX, float posY, int hp){
		Random rand = new Random();
		
		InfectoGameObject object = new InfectoGameObject();
		
		TextureLibrary texLib = gamePointers.textureLib;
		DrawableBitmap[] dbs = new DrawableBitmap[2];
		
		Texture f1 = texLib.allocateTexture(R.drawable.manwalk_s_1);
		Texture f2 = texLib.allocateTexture(R.drawable.manwalk_s_2);
		
		int size = 64;
		dbs[0] = new DrawableBitmap(f1, size, size);
		dbs[1] = new DrawableBitmap(f2, size, size);
		
		LAnimation moveAnimation = new LAnimation(dbs, 0.2f);

		
		SpriteComponent sprite = new SpriteComponent();
		sprite.setAnimation(SpriteState.idle, moveAnimation);
		
		int width = gamePointers.map.getWidth();
		int height = gamePointers.map.getHeight();
		
		object.pos.x = posX == -1? rand.nextInt(width) : posX;
		object.pos.y = posY == -1? rand.nextInt(height): posY;
		
		object.collisionObject = 
			new CollisionCircle(Team.Human.ordinal(), object.pos, object, (float) (size/2.0));
		
		object.addComponent(new CollisionComponent());
		object.addComponent(sprite); 
		object.addComponent(new BehaviorComponent(object));
		object.addComponent(new HpBarComponent());
		
		object.speed = rand.nextInt(20)+20;
		
		return object;
	}

	@Override
	public void update(float dt, BaseObject parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
