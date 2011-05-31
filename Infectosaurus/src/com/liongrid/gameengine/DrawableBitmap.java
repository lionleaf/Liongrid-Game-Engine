package com.liongrid.gameengine;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;


/**
 * @author Lionleaf
 *
 */
public class DrawableBitmap extends BaseObject implements DrawableObject {
	private Texture mTexture;
	private int mWidth;
	private int mHeight;
	private int mCrop[];
	private float mOpacity;

	public DrawableBitmap(Texture texture, int width, int height) {
		super();
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
    

	/*private void loadGLTextures(GL10 gl) {
		//		Log.d(TAG, "Loading texture");
		//		//http://blog.poweredbytoast.com/loading-opengl-textures-in-android  :
		//		
		//		int[] tempArr = new int[1];
		//	    gl.glGenTextures(1, tempArr, 0);
		//		
		//	    int textureID = tempArr[0];
		//		// We need to flip the textures vertically:
		//	    Matrix flip = new Matrix();
		//	    flip.postScale(1f, -1f);
		//	    
		//	    // This will tell the BitmapFactory to not scale based on the device's pixel density:
		//	    // (Thanks to Matthew Marshall for this bit)
		//	    BitmapFactory.Options opts = new BitmapFactory.Options();
		//	    opts.inScaled = false;
		//	    
		//	    // Load up, and flip the texture:
		//	    Bitmap temp = BitmapFactory.decodeResource(context.getResources(), resource, opts);
		//	    Bitmap bmp = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), flip, true);
		//	    temp.recycle();
		//	    
		//	    gl.glBindTexture(GL10.GL_TEXTURE_2D, textureID);
		//	    
		//	    // Set all of our texture parameters:
		//	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
		//	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
		//	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		//	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		//	    
		//	    
		//	    int[] crop = {0, bmp.getWidth(), bmp.getHeight(), -bmp.getHeight()};
		//
		//	    ((GL11) gl).glTexParameteriv(GL10.GL_TEXTURE_2D, 
		//	    GL11Ext.GL_TEXTURE_CROP_RECT_OES, crop, 0);
		//	    
		//	    
		//	    // Generate, and load up all of the mipmaps:
		//	    for(int level=0, height = bmp.getHeight(), width = bmp.getWidth(); true; level++) {
		//	        // Push the bitmap onto the GPU:
		//	        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bmp, 0);
		//	        
		//	        // We need to stop when the texture is 1x1:
		//	        if(height==1 && width==1) break;
		//	        
		//	        // Resize, and let's go again:
		//	        width >>= 1; height >>= 1;
		//	        if(width<1)  width = 1;
		//	        if(height<1) height = 1;
		//	        
		//	        Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, width, height, true);
		//	        bmp.recycle();
		//	        bmp = bmp2;
		//	    }
		//	    
		//	    bmp.recycle();
		//	    gl.glEnable(GL10.GL_TEXTURE_2D);
		//	    
		// Code from replica island
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		textureID = textures[0];

		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);

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
	} */
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

	@Override
	public void update(float dt, BaseObject parent) {
		// This is just here so to make it a base object
	}
}
