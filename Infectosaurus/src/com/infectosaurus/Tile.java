package com.infectosaurus;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

public class Tile {
	private int mTextureID = -1;
	private boolean shoudlLoadTexture = true;
	private Bitmap mBitmap;
	private GameObject gameObject;
	private int cameraPosX;
	private int cameraPosY;
	private int mapSizeX = 10;
	private int mapSizeY = 10;
	private GL10 gl;
	private static int tileSize = 32;
	
	Tile(GL10 gl, Bitmap bitmap){
		this.gl = gl;
		// Clears the screen and depth buffer.
		mBitmap = bitmap;
		setUp();
	}
	
	private void setUp() {
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
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

	public void clearTile(int x, int y){
		setTileTexture(x, y);
	}
	
	private void setTileTexture(int x, int y) {
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		mTextureID = textures[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, mBitmap, 0);
	}

	public int[] getTile(int x, int y){
		int tileX = (cameraPosX + x)/tileSize;
		int tileY = (cameraPosY + y)/tileSize;
		int[] rValue = {tileX, tileY};
		return rValue;
	}
	
	public void update4Renderer(GL10 gl) {
		
		if (mTextureID != -1) {
			gl.glEnable(GL10.GL_TEXTURE_2D);
			// Point to our buffers
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);
		}
		
		((GL11Ext) gl).glDrawTexfOES(gameObject.posX, gameObject.posY, 0, 100, 100); 

		if (mTextureID != -1) {
			gl.glDisable(GL10.GL_TEXTURE_2D);
		}
	}
	
	private void loadGLTextures(GL10 gl) {

		
	}
}
