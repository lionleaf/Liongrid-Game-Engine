package com.infectosaurus;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class Tile {
	private int mTextureID = -1;
	private boolean shoudlLoadTexture = true;
	private Bitmap mBitmap;
	private int cameraPosX = 0;
	private int cameraPosY = 0;
	private static int tileSize = 64;
	
	Tile(Panel panel){
		mBitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.scrub);
	}
	

	public void clearTile(GL10 gl, int tileX, int tileY){
		if(shoudlLoadTexture){
			loadGLTextures(gl);
			shoudlLoadTexture = false;
		}
		
		if (mTextureID != -1) {
			gl.glEnable(GL10.GL_TEXTURE_2D);

			// Point to our buffers
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);
		}
		int[] i = getAbsTilePos(tileX, tileY);
		int x = i[0];
		int y = i[1];
		
		((GL11Ext) gl).glDrawTexfOES(x, y, 0, tileSize, tileSize); 

		if (mTextureID != -1) {
			gl.glDisable(GL10.GL_TEXTURE_2D);
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

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
		

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
}
