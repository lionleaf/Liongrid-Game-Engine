package com.liongrid.infectosaurus;

import java.util.Random;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LCollisionCircle;
import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LAnimation;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LTexture;
import com.liongrid.gameengine.LTextureLibrary;
import com.liongrid.infectosaurus.components.IBehaviorComponent;
import com.liongrid.infectosaurus.components.IHpBarComponent;
import com.liongrid.infectosaurus.components.ISpriteComponent;

public class ISpawnPool extends LBaseObject{
		
	public IGameObject spawnMinion(){
		IGameObject object = new IGameObject();
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
		
		object.mHeigth = 64;
		object.mWidth = 64;
		dbs[0] = new LDrawableBitmap(f1, object.mWidth, object.mHeigth);
		dbs[1] = new LDrawableBitmap(f2, object.mWidth, object.mHeigth);
		
		LDrawableBitmap[] stand = new LDrawableBitmap[]{new LDrawableBitmap
				(f3, object.mWidth, object.mHeigth)};
		
		
		LAnimation moveAnimation = new LAnimation(dbs, 0.2f,true);
		LAnimation standAnimation = new LAnimation(stand, 1f, true);
		
		ISpriteComponent sprite = new ISpriteComponent();
		sprite.addAnimation("IWalk", moveAnimation);
		sprite.addAnimation("IStand", standAnimation);
		
		int mapWidth = LGamePointers.map.getWidth();
		int mapHeight = LGamePointers.map.getHeight();
		
		object.pos.x = posX == -1? rand.nextInt(mapWidth) : posX;
		object.pos.y = posY == -1? rand.nextInt(mapHeight): posY;
		
		object.collisionObject = 
			new LCollisionCircle(ITeam.Human.ordinal(), object, (float) (object.mWidth/2.0));
		
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
	
	
	
	
}
