package com.infectosaurus.components;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;
import android.graphics.Bitmap;
import android.opengl.GLUtils;
import android.util.Log;

import com.infectosaurus.GameObject;

public class AnimationComponent extends Component {
	private int mTextureID = -1;
	private boolean shoudlLoadTexture = true;
	private Bitmap mBitmap;
	private GameObject gameObject;

	public AnimationComponent(GameObject o, Bitmap bitmap) {
		this.gameObject = o;
		mBitmap = bitmap;
	}
	@Override
	public void update4Game() {

	}
	@Override
	public void update4Renderer(GL10 gl) {
		Log.d("GameBoard", "In AnimationComponent");
		//if(posX > 2) posX = -2f;
		//		else posX += 0.1;
			
		if(shoudlLoadTexture){
			loadGLTextures(gl);
			shoudlLoadTexture = false;
		}
		
		if (mTextureID != -1) {
			gl.glEnable(GL10.GL_TEXTURE_2D);

			// Point to our buffers
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);
		}
		gl.glTranslatef(gameObject.posX, gameObject.posY, 0);
		
		((GL11Ext) gl).glDrawTexfOES(1, 1, 0, 300, 300); 

		if (mTextureID != -1) {
			gl.glDisable(GL10.GL_TEXTURE_2D);
		}
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

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_CLAMP_TO_EDGE);
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

}
