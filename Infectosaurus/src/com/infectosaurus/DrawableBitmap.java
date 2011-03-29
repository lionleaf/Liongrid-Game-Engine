package com.infectosaurus;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

public class DrawableBitmap implements Drawable {
//	private Texture mTexture;
	private int mWidth;
	private int mHeight;
	private int mCrop[];
	private int mViewWidth;
	private int mViewHeight;
	private float mOpacity;
	private boolean loaded = false;
	private int textureID = -1;

	private Bitmap bitmap;
	
	DrawableBitmap(Bitmap bitmap, int width, int height) {

		//TODO remove
		mWidth = width;
		mHeight = height;
		mCrop = new int[4];
		mViewWidth = 0;
		mViewHeight = 0;
		mOpacity = 1.0f;
		setCrop(0, height, width, height);
		this.bitmap = bitmap;
	}

	public void reset() {
		mViewWidth = 0;
		mViewHeight = 0;
		mOpacity = 1.0f;

	}
	
    public static void beginDrawing(GL10 gl, float viewWidth, float viewHeight) {
    	gl.glShadeModel(GL10.GL_FLAT);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glColor4x(0x10000, 0x10000, 0x10000, 0x10000);

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glOrthof(0.0f, viewWidth, 0.0f, viewHeight, 0.0f, 1.0f);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();
       
        gl.glEnable(GL10.GL_TEXTURE_2D);
    }
	
    public static void endDrawing(GL10 gl) {
    	gl.glDisable(GL10.GL_BLEND);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glPopMatrix();
    }

	public void draw(GL10 gl, float x, float y, float scaleX, float scaleY) {
		//GL10 gl = OpenGLSystem.getGL();
		if(!loaded ){
			loadGLTextures(gl);
			loaded = true;
		}
		if (gl != null && textureID > 0) {
			// Point to our buffers, bind texture is also done in loadGLTextures, but
			// we need to do it again so the saved textureID will be loaded a second time
			// the bitmap is drawn.
			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
			((GL11Ext) gl).glDrawTexfOES(x, y, 0, (float) mWidth, (float) mHeight); 
		}
	}


	private void loadGLTextures(GL10 gl) {
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		textureID = textures[0];

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_CLAMP_TO_EDGE);
		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,
				GL10.GL_MODULATE);

		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		int[] mCropWorkspace = new int[4];
		mCropWorkspace[0] = 0;
		mCropWorkspace[1] = bitmap.getHeight();
		mCropWorkspace[2] = bitmap.getWidth();
		mCropWorkspace[3] = -bitmap.getHeight();
		((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, 
				GL11Ext.GL_TEXTURE_CROP_RECT_OES, 
				mCropWorkspace,
				0);
		mCrop = mCropWorkspace;
		bitmap.recycle();
	}

	public void resize(int width, int height) {
		mWidth = width;
		mHeight = height;
		setCrop(0, height, width, height);
	}

	public int getWidth() {
		return mWidth;
	}

	public void setWidth(int width) {
		mWidth = width;
	}


	public int getHeight() {
		return mHeight;
	}

	public void setHeight(int height) {
		mHeight = height;
	}

	/**
	 * Changes the crop parameters of this bitmap.  Note that the underlying OpenGL texture's
	 * parameters are not changed immediately The crop is updated on the
	 * next call to draw().  Note that the image may be flipped by providing a negative width or
	 * height.
	 *
	 * @param left
	 * @param bottom
	 * @param width
	 * @param height
	 */
	public void setCrop(int left, int bottom, int width, int height) {
		// Negative width and height values will flip the image.
		mCrop[0] = left;
		mCrop[1] = bottom;
		mCrop[2] = width;
		mCrop[3] = -height;
	}
}
