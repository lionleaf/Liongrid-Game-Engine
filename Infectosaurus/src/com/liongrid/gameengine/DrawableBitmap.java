package com.liongrid.gameengine;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;


/**
 * @author Lionleaf
 *
 */
public class DrawableBitmap extends BaseObject implements DrawableObject {
	public boolean mCameraRelative;
	private Texture mTexture;
	private int mWidth;
	private int mHeight;
	private int mCrop[];
	private float mOpacity;

	/**
	 * @param texture - texture of the bitmap
	 * @param width - width of the object to be drawn in in-game coordinates
	 * @param height - height of the object to be drawn in in-game coordinates
	 */
	public DrawableBitmap(Texture texture, int width, int height, 
			boolean cameraRelative) {
		
		super();
		mCameraRelative = cameraRelative;
		mTexture = texture;
		mWidth = width;
		mHeight = height;
		mCrop = new int[4];
		mOpacity = 1.0f;
		setCrop(0, height, width, height);
	}

	public void reset() {
		mTexture = null;
		mOpacity = 1.0f;
	}

	public void setOpacity(float opacity) {
        mOpacity = opacity;
    }
	
	/**
	 * Set up the OpenGL for drawing, should be called once before every frame
	 * @param gl - The GL10 pointer
	 * @param viewWidth - Width
	 * @param viewHeight - Height
	 */
	public static void beginDrawing(GL10 gl, float viewWidth, float viewHeight) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		gl.glShadeModel(GL10.GL_FLAT);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glPushMatrix();
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glOrthof(0.0f, viewWidth, 0.0f, viewHeight, 0.0f, 1.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		
		gl.glEnable(GL10.GL_TEXTURE_2D);


	}


	/**
	 * OpenGL code to be executed after every frame is drawn
	 * @param gl
	 */
	public static void endDrawing(GL10 gl) {
		gl.glDisable(GL10.GL_BLEND);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glPopMatrix();
	}

	/**
     * Draw the bitmap at a given x,y position, expressed in pixels, with the
     * lower-left-hand-corner of the view being (0,0).
     *
     * @param gl  A pointer to the OpenGL context
     * @param x  The number of pixels to offset this drawable's origin in the x-axis.
     * @param y  The number of pixels to offset this drawable's origin in the y-axis
     * @param scaleX The horizontal scale factor between the bitmap resolution and the display resolution.
     * @param scaleY The vertical scale factor between the bitmap resolution and the display resolution.
     */
	public void draw(GL10 gl, float x, float y, float scaleX, float scaleY) {

		//GL10 gl = OpenGLSystem.getGL();
		if (gl != null && mTexture != null) {
			// Point to our buffers, bind texture is also done in loadGLTextures, but
			// we need to do it again so the saved textureID will be loaded a second time
			// the bitmap is drawn.
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTexture.id);
			
			// This is necessary because we could be drawing the same texture with different
            // crop (say, flipped horizontally) on the same frame.
            //OpenGLSystem.setTextureCrop(mCrop);
			
			((GL11Ext) gl).glDrawTexfOES(Math.round(x*scaleX), 
					Math.round(y*scaleY), 
					0, 
					(float) mWidth *scaleX, 
					(float) mHeight*scaleY); 
		}
	}


	public void setTexture(Texture texture) {
        mTexture = texture;
    }


    public Texture getTexture() {
        return mTexture;
    }
    
    protected final void setFlip(boolean horzFlip, boolean vertFlip) {
        setCrop(horzFlip ? mWidth : 0,
                vertFlip ? 0 : mHeight,
                horzFlip ? -mWidth : mWidth,
                vertFlip ? -mHeight : mHeight);
    }
    

	public int getWidth() {
		return mWidth;
	}


	public int getHeight() {
		return mHeight;
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
	
	/**
	 * Set`s the width of the drawable bitmap. Rescales it.
	 * @param width - the new width
	 */
	public void setWidth(int width){
		mWidth = width;
	}
	
	/**
	 * Set`s the height of the drawable bitmap. Rescales it.
	 * @param height - the new height
	 */
	public void setHeight(int height){
		mHeight= height;
	}

	@Override
	public void update(float dt, BaseObject parent) {
		// This is just here so to make it a base object
	}

	public boolean isCameraRelative() {
		return mCameraRelative;
	}
}
