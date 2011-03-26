package com.infectosaurus;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Tile extends BaseObject {
	private int mTextureID = -1;
	private boolean shoudlLoadTexture = true;
	private Bitmap mBitmap;
	private int cameraPosX = 0;
	private int cameraPosY = 0;
	private static int tileSize = 64;
	Vector2 pos;
	DrawableBitmap drawBitmap;
	RenderSystem rSys;

	private Vector2 mapSize = new Vector2();

	private int refreshTime = 60;
	int rcounter = 0;


	Tile(){
		Panel panel = BaseObject.gamePointers.panel;
		mBitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.scrub);
		drawBitmap = new DrawableBitmap(mBitmap, tileSize, tileSize);
		pos = new Vector2();
		rSys = BaseObject.gamePointers.renderSystem;
		mapSize.x = 10;
		mapSize.y = 10;
	}

	@Override
	public void update(float dt, BaseObject parent){
		if(rcounter >= refreshTime){
			refreshMap();
			rcounter = 0;
		}else{
			rcounter++;
		}
	}

	public void clearTile(int tileX, int tileY){

		int[] i = getAbsTilePos(tileX, tileY);
		pos.x = i[0];
		pos.y = i[1];
		rSys.scheduleForDraw(drawBitmap, pos);
	}

	public void refreshMap(){
		for(int x = 0; x < mapSize.x ; x++){
			for(int y = 0 ; y < mapSize.y ; y++){
				clearTile(x,y);
			}
		}
	}


	private int[] getAbsTilePos(int x, int y) {
		int X = x*tileSize - cameraPosX;
		int Y = y*tileSize - cameraPosY;
		int[] i = {X,Y};
		return i;

	}

	public int[] getTile(int x, int y){
		int tileX = (cameraPosX + x)/tileSize;
		int tileY = (cameraPosY + y)/tileSize;
		int[] rValue = {tileX, tileY};
		return rValue;
	}
	private void loadGLTextures(GL10 gl) {
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		mTextureID = textures[0];

		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);
		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,
				GL10.GL_REPLACE);


		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
		int[] mCropWorkspace = new int[4];
		mCropWorkspace [0] = 0;
		mCropWorkspace[1] = mBitmap.getHeight();
		mCropWorkspace[2] = mBitmap.getWidth();
		mCropWorkspace[3] = -mBitmap.getHeight();
		((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, 
				GL11Ext.GL_TEXTURE_CROP_RECT_OES, 
				mCropWorkspace,
				0);

		mBitmap.recycle();
	}


	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}
}
