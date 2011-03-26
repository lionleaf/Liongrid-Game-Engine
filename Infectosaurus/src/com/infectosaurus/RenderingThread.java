package com.infectosaurus;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

import android.opengl.GLU;
import android.util.Log;


public class RenderingThread implements Panel.Renderer {
    
    private static final String TAG = "My Activity";
    private ObjectHandler drawQueue;
	private Object drawLock;
	private boolean drawQueueChanged;
    
 
    public RenderingThread() {
    	Log.d(TAG,"In RThread");
    	drawLock = new Object();
    }

	public void onDrawFrame(GL10 gl) {
		if(OpenGLSystem.gl == null) OpenGLSystem.gl = gl;
		
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
			
			DrawableBitmap.beginDrawing(gl, 500, 500);
			if (drawQueue != null && drawQueue.getObjects().getCount() > 0){
				FixedSizeArray<BaseObject> objects = drawQueue.getObjects();
				final int count = objects.getCount();
				for (int i = 0; i< count; i++){
					
					RenderElement elem = (RenderElement) objects.get(i);
					if(elem == null){
						Log.d("RENDER", "elem " + elem );
						continue;
					}
					elem.drawable.draw(elem.x, elem.y, 1, 1);
				}
				DrawableBitmap.endDrawing(gl);
			}
		}
		// Clears the screen and depth buffer.
		// Replace the current matrix with the identity matrix
		//
		//gameHandler.update4Renderer(gl);
		// Disable face culling.
		gl.glDisable(GL10.GL_CULL_FACE); 
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
		// Textures are not rendered outside the view... i think
		gl.glOrthof(-width/2, width/2, -height/2, height/2, 1, 10);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// Counter-clockwise winding.
		gl.glFrontFace(GL10.GL_CCW); // OpenGL docs
		// Enable face culling.
		gl.glEnable(GL10.GL_CULL_FACE); // OpenGL docs
		// What faces to remove with the face culling.
		gl.glCullFace(GL10.GL_BACK); // OpenGL docs
		// Set the background color to black ( rgba ).
		gl.glClearColor(1.0f, 0.0f, 0.0f, 0.5f);
		// Enable transparency
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	}

	public void setDrawQueue(ObjectHandler drawQueue) {
		this.drawQueue = drawQueue;
		synchronized(drawLock) {
            drawQueueChanged = true;
            drawLock.notify();
		}
	}
}
