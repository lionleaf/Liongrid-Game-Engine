package com.infectosaurus;

import java.nio.Buffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.infectosaurus.components.MeleeAttackComponent;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class Infectosaurus extends GameObject{
	private static final String TAG = "GameBoard";
	private static final int FIXED_SIZE = 4;
	private Bitmap bitmap;
	private Buffer mVertexBuffer;
	private int mVertBufferIndex;
	
	Infectosaurus(Panel panel){
		bitmap = BitmapFactory.decodeResource(panel.getResources(), 
				R.drawable.lumberinghulklo);
		addGameComponent(new MeleeAttackComponent());
	}
	
	@Override
	public void useComp4Renderer(GL10 gl) {
		Log.d(TAG, "Ready to go sir!");
		beginDrawing(gl);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		// paramaters in next line should be given in GameObject, but it's not fixed yet
		gl.glTranslatef(posX,posY,-4);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		int[] buffer = new int[1];
		GL11 gl11 = (GL11) gl;
		gl11.glGenBuffers(1, buffer,0 );
		gl11.glGenBuffers(1, buffer, 0);
        mVertBufferIndex = buffer[0];
        gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, mVertBufferIndex);
        final int vertexSize = mVertexBuffer.capacity() * FIXED_SIZE; 
        gl11.glBufferData(GL11.GL_ARRAY_BUFFER, vertexSize, 
                mVertexBuffer, GL11.GL_STATIC_DRAW);
		gl.glPopMatrix();
		endDrawing(gl);
	}

	private void endDrawing(GL10 gl) {
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

	private void beginDrawing(GL10 gl) {
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glEnable(GL10.GL_TEXTURE_2D);
	}
}
