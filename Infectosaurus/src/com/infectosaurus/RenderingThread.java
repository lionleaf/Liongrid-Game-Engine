package com.infectosaurus;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glLoadIdentity();
		gl.glTranslatef(0, 0, -4);
		gameObjects.update4Renderer(gl);
	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(1.0f, 0.0f, 0.0f, 1f);
	}
}
