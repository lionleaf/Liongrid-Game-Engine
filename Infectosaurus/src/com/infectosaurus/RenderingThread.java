package com.infectosaurus;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.view.SurfaceHolder;



public class RenderingThread implements Panel.Renderer {
    
    private Panel mPanel;
    private SurfaceHolder mHolder; 
 
    public RenderingThread(Panel panel) {
        mPanel = panel;
        mHolder = mPanel.getHolder();
    }

 	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		
	}
}
