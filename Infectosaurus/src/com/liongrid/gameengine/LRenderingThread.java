package com.liongrid.gameengine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.liongrid.gameengine.tools.LFixedSizeArray;
import com.liongrid.infectosaurus.Main;
import com.liongrid.infectosaurus.map.Map;
import com.liongrid.infectosaurus.map.TileType;

import android.graphics.Bitmap;
import android.util.Log;


public class LRenderingThread implements LPanel.Renderer {
	static final int TILE_SIZE = Map.TILE_SIZE;
	
    private LObjectHandler drawQueue;
	private Object drawLock;
	private boolean drawQueueChanged;

	private boolean screenshot = false;

	public Bitmap lastScreenshot;
    
 
    public LRenderingThread() {
    	Log.d(Main.TAG,"In RThread");
    	drawLock = new Object();
    }
    
    public synchronized void takeScreenShot(){
    	screenshot = true;
    }

	public void onDrawFrame(GL10 gl) { 
		LOpenGLSystem.gl = gl;
		//Avoid drawing same scene twice
		synchronized(drawLock) {
            if (!drawQueueChanged) {
                while (!drawQueueChanged) {
                    try {
                        drawLock.wait();
                    } catch (InterruptedException e) {
                        // No big deal if this wait is interrupted.
                    }
                }
            }
            drawQueueChanged = false;
        }
		
		synchronized (this) {
			int mWidth = LBaseObject.gamePointers.panel.getWidth();
			int mHeight= LBaseObject.gamePointers.panel.getHeight();
			
			//Get current camera state, to avoid different 
			//camera throughout drawing
			//TODO We pretty much copy the whole camera.
			// Different solution? Not static?
			
			int cameraX = LCamera.pos.x;
			int cameraY = LCamera.pos.y;
			int cameraWidth = LCamera.screenWidth;
			int cameraHeight = LCamera.screenHeight;
			float scale = LCamera.scale;
			
			
			
			LDrawableBitmap.beginDrawing(gl, mWidth, mHeight);
			
			//Draw tiles
			drawTiles(gl, cameraX, cameraY, cameraWidth, cameraHeight, scale);
			
			if (drawQueue != null && drawQueue.getObjects().getCount() > 0 ){
				drawObjects(gl, cameraX, cameraY, cameraWidth, cameraHeight, scale);
			}
			
			LDrawableBitmap.endDrawing(gl);
			
			if(screenshot){                     
                snapScreenshot(gl, mWidth, mHeight);
            }
		}
	}


	private void drawObjects(GL10 gl, int cameraX, int cameraY, int cameraWidth, float cameraHeight, float scale){
		LFixedSizeArray<LRenderElement> objects = drawQueue.getObjects();
		final int count = objects.getCount();
		Object[] elems = objects.getArray();
		objects.sort(true);

		for (int i = 0; i < count; i++){	
			LRenderElement elem = (LRenderElement)elems[i];
			
			if(elems[i] == null){ 
				Log.d(Main.TAG, "elem in drawBGQueue is " + elem + 
						"Last count was " + count + " Now it is "+ objects.getCount());
				continue;
			}
			if(!elem.cameraRelative){ // not camera relative (not a HUD image)
				elem.drawable.draw(gl, 
						elem.x - cameraX, 
						elem.y - cameraY, 
						scale, 
						scale);
			} else {
				
				elem.drawable.draw(gl, elem.x, elem.y, elem.scale, elem.scale);
			}
		}
	}
	
	private void drawTiles(GL10 gl, int cameraX, int cameraY, int cameraWidth, float cameraHeight, float scale){
		Map level = LBaseObject.gamePointers.map;
		TileType[][] bgTiles = level.renderQueue;
		
		if(bgTiles != null && bgTiles.length > 0){
			int tilesX =  (int)(cameraX + cameraWidth/scale)/TILE_SIZE + 1;
			if(tilesX > Map.size.x) tilesX = Map.size.x;
			int tilesY =  (int)(cameraY + cameraHeight/scale)/TILE_SIZE + 1;
			if(tilesY > Map.size.y) tilesY = Map.size.y;
			for (int i = cameraX / Map.TILE_SIZE; i < tilesX; i++) {
				for (int j = cameraY / Map.TILE_SIZE; j < tilesY; j++) {
					int x = Map.TILE_SIZE*i;
					int y = Map.TILE_SIZE*j;
					//Check if element is outside the screen view
			        if(x + Map.TILE_SIZE < cameraX/scale) continue;
			    	if(x > cameraX + cameraWidth/scale) continue;
			        if(y + Map.TILE_SIZE < cameraY) continue;
			    	if(y > cameraY + cameraHeight/scale) continue;
			    	
					bgTiles[i][j].draw(gl, x - cameraX
							, y - cameraY, scale, scale);
				}
			}
		}
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
        
        /*
         * Set our projection matrix. This doesn't have to be done each time we
         * draw, but usually a new projection needs to be set when the viewport
         * is resized.
         */
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        float ratio = (float)width/height;
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        
        //Load textures TODO move this to the best place
        
        
	}


	private void snapScreenshot(GL10 gl, int width, int height){
		int screenshotSize = width * height;
        ByteBuffer bb = ByteBuffer.allocateDirect(screenshotSize * 4);
        bb.order(ByteOrder.nativeOrder());
        gl.glReadPixels(0, 0, width, height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, bb);
        int pixelsBuffer[] = new int[screenshotSize];
        bb.asIntBuffer().get(pixelsBuffer);
        bb = null;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixelsBuffer, screenshotSize-width, -width, 0, 0, width, height);
        pixelsBuffer = null;

        short sBuffer[] = new short[screenshotSize];
        ShortBuffer sb = ShortBuffer.wrap(sBuffer);
        bitmap.copyPixelsToBuffer(sb);

        //Making created bitmap (from OpenGL points) compatible with Android bitmap
        for (int i = 0; i < screenshotSize; ++i) {                  
            short v = sBuffer[i];
            sBuffer[i] = (short) (((v&0x1f) << 11) | (v&0x7e0) | ((v&0xf800) >> 11));
        }
        sb.rewind();
        bitmap.copyPixelsFromBuffer(sb);
        lastScreenshot = bitmap;

        screenshot = false;
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		 /*
         * Some one-time OpenGL initialization can be made here probably based
         * on features of this particular context
         */
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
        gl.glShadeModel(GL10.GL_FLAT);
        gl.glDisable(GL10.GL_DEPTH_TEST);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        /*
         * By default, OpenGL enables features that improve quality but reduce
         * performance. One might want to tweak that especially on software
         * renderer.
         */
        gl.glDisable(GL10.GL_DITHER);
        gl.glDisable(GL10.GL_LIGHTING);

        gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        
        LBaseObject.gamePointers.textureLib.invalidateAll();
        LBaseObject.gamePointers.textureLib.loadAll(
        		LBaseObject.gamePointers.panel.getContext(), gl);
	}

	public synchronized void setDrawQueue(LObjectHandler drawQueue) {
		this.drawQueue = drawQueue;
		synchronized(drawLock) {
            drawQueueChanged = true;
            drawLock.notify();
		}
	}
	
	
	
	/**
     * This function blocks while drawFrame() is in progress, and may be used by other threads to
     * determine when drawing is occurring.
     */	
	public synchronized void waitDrawingComplete() {
		
	}
}