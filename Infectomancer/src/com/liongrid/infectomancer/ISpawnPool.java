package com.liongrid.infectomancer;

import java.util.Random;

import com.liongrid.gameengine.LAnimationCodes;
import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCollisionCircle;
import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LAnimation;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LTexture;
import com.liongrid.gameengine.LTextureLibrary;
import com.liongrid.gameengine.components.LMoveComponent;
import com.liongrid.gameengine.components.LSpriteComponent;
import com.liongrid.infectomancer.components.IAggressivMoveComponent;
import com.liongrid.infectomancer.components.IBehaviorComponent;
import com.liongrid.infectomancer.components.ICollisionComponent;
import com.liongrid.infectomancer.components.IHpBarComponent;
import com.liongrid.infectomancer.components.IMeleeAttackComponent;
import com.liongrid.infectomancer.effects.IDOTEffect;
import com.liongrid.infectomancer.R;

public class ISpawnPool extends LBaseObject{
	
	public IGameObject spawnMinion(float x, float y){
		IGameObject object = new IGameObject();
		
		int size = 16*3;
		float radius = (float) (size/2.0);
		object.collisionObject = new LCollisionCircle(ITeam.Alien.ordinal(), object, radius);
		
		LTextureLibrary texLib = LGamePointers.textureLib;
		LTexture tex = texLib.allocateTexture(R.drawable.squaremonster);
		
		LSpriteComponent sprite = new LSpriteComponent();
		LDrawableBitmap[] dbs = new LDrawableBitmap[4];
		LDrawableBitmap[] attackBmps = new LDrawableBitmap[1];
		LDrawableBitmap[] spawnBmps = new LDrawableBitmap[5];
		
		
		dbs[0] = new LDrawableBitmap(tex, size,   size);
		dbs[1] = new LDrawableBitmap(tex, size+3, size+3);
		dbs[2] = new LDrawableBitmap(tex, size+6, size+6);
		dbs[3] = new LDrawableBitmap(tex, size+3, size+3);
		
		
		spawnBmps[0] = new LDrawableBitmap(tex, size/6, size/6);
		spawnBmps[1] = new LDrawableBitmap(tex, 2*size/6, 2*size/6);
		spawnBmps[2] = new LDrawableBitmap(tex, 3*size/6, 3*size/6);
		spawnBmps[3] = new LDrawableBitmap(tex, 4*size/6, 4*size/6);
		spawnBmps[4] = new LDrawableBitmap(tex, 5*size/6, 5*size/6);
		
		
		attackBmps[0] = new LDrawableBitmap(tex, size+25, size+25);
		
		LAnimation moveAnimation = new LAnimation(dbs, 0.1f, true);
		LAnimation attackAnimation = new LAnimation(attackBmps, 0.1f, false);
		LAnimation spawnAnimation = new LAnimation(spawnBmps, 0.06f, false);
		
		sprite.addAnimation("Move", moveAnimation);
		sprite.addAnimation("Attack", attackAnimation);
		sprite.addAnimation("Spawning", spawnAnimation);
		
		sprite.setOverlayAnimation("Spawning");
		IMeleeAttackComponent attackComponent = new IMeleeAttackComponent();
		attackComponent.setInfect(false);
		object.addComponent(new ICollisionComponent());
		object.addComponent(attackComponent);
		object.addComponent(new IAggressivMoveComponent());
		object.addComponent(sprite);
		object.addComponent(new LMoveComponent());
		object.addComponent(new IHpBarComponent());
		
		
		
		object.pos.x = x;
		object.pos.y = y;
		object.speed = 80;
		
		object.team = ITeam.Alien;
		
		int diff = IGamePointers.difficulty*3;
		//Temp stuff to die in x sec
		IDOTEffect e = new IDOTEffect();
		e.set(Float.MAX_VALUE, diff, 1f);
		object.afflict(e);
		
		object.mMaxHp = 15;
		object.mHp = 15;
		return object;
	}
	

	public static IGameObject spawnInfectosaurus(){
		IGameObject object = new Infectosaurus();
		return object;
	}
	
	/**
	 * @param posX -1 for random
	 * @param posY -1 for random
	 * @param hp
	 * @return
	 */
	public static IGameObject spawnHuman(float posX, float posY, int hp){
		Random rand = new Random();
		
		IGameObject object = new IGameObject();
		
		LTextureLibrary texLib = LGamePointers.textureLib;
		LDrawableBitmap[] dbs = new LDrawableBitmap[2];
		
		LTexture f1 = texLib.allocateTexture(R.drawable.manwalk_s_1);
		LTexture f2 = texLib.allocateTexture(R.drawable.manwalk_s_2);
		LTexture f3 = texLib.allocateTexture(R.drawable.manidle);
		
		object.heigth = 64;
		object.width = 64;
		dbs[0] = new LDrawableBitmap(f1, object.width, object.heigth);
		dbs[1] = new LDrawableBitmap(f2, object.width, object.heigth);
		
		LDrawableBitmap[] stand = new LDrawableBitmap[]{new LDrawableBitmap
				(f3, object.width, object.heigth)};
		
		
		LAnimation moveAnimation = new LAnimation(dbs, 0.2f,true);
		LAnimation standAnimation = new LAnimation(stand, 1f, true);
		
		LSpriteComponent sprite = new LSpriteComponent();
		sprite.addAnimation(LAnimationCodes.WALK_SOUTH, moveAnimation);
		sprite.addAnimation("IStand", standAnimation);
		
		int mapWidth = LGamePointers.map.getWidth();
		int mapHeight = LGamePointers.map.getHeight();
		
		object.pos.x = posX == -1? rand.nextInt(mapWidth) : posX;
		object.pos.y = posY == -1? rand.nextInt(mapHeight): posY;
		
		object.collisionObject = 
			new LCollisionCircle(ITeam.Human.ordinal(), object, (float) (object.width/2.0));
		
//		object.addComponent(new ICollisionComponent());
		object.addComponent(sprite); 
		object.addComponent(new IBehaviorComponent(object));
		object.addComponent(new IHpBarComponent());
		
		object.speed = rand.nextInt(20)+20;
		object.mMaxHp = hp;
		object.mHp = hp;
		
		return object;
	}

	@Override
	public void update(float dt, LBaseObject parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}


	public void spawnNPCs(int pop, int difficulty) {
		IGameObjectHandler handler = IGamePointers.gameObjectHandler;
		for (int i = 0; i < pop; i++){
			IGameObject newHuman = spawnHuman(-1,-1,2*difficulty);
			handler.add(newHuman);
		}
		
	}
	
	
	
	
}
