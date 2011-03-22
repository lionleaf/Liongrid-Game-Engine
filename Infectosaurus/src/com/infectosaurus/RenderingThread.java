package com.infectosaurus;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.util.Log;


public class RenderingThread implements Panel.Renderer {
    
    private static final String TAG = "My Activity";
	private Panel mPanel;
    GameObjectHandler gameObjects;
 
    public RenderingThread(Panel panel) {
    	Log.d(TAG,"In RThread");
        mPanel = panel;
        gameObjects = new GameObjectHandler(panel);
    }

	public void onDrawFrame(GL10 gl) {
		// Clears the screen and depth buffer.
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		// Replace the current matrix with the identity matrix
		gl.glLoadIdentity();
		// Translates 4 units into the screen.
		gl.glTranslatef(0, 0, -4);
		gl.glScalef(0.5f, 0.5f, 0f);
		
		gameObjects.update4Renderer(gl);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// Sets the current view port to the new size.
		gl.glViewport(0, 0, width, height);
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
		// Set the background color to black ( rgba ).
		gl.glClearColor(1.0f, 0.0f, 0.0f, 0.5f);
	}
}
