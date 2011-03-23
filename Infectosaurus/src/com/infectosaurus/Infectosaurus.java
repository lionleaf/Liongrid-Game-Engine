package com.infectosaurus;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11Ext;

import com.infectosaurus.components.MeleeAttackComponent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

public class Infectosaurus extends GameObject {
	private static final String TAG = "GameBoard";
	private Bitmap bitmap;
	private int mTextureID = -1;
	private boolean shoudlLoadTexture = true;
	private FloatBuffer mTextureBuffer;
	private FloatBuffer mVerticesBuffer;
	private ShortBuffer mIndicesBuffer;
	private int mNumOfIndices;

	Infectosaurus(Panel panel) {
		bitmap = BitmapFactory.decodeResource(panel.getResources(),
				R.drawable.lumberinghulklo);
		addGameComponent(new MeleeAttackComponent());

		// Mapping coordinates for the vertices
		float textureCoordinates[] = { 0.0f, 1.0f, //
				0.0f, 0.0f, //
				1.0f, 0.0f, //
				1.0f, 1.0f, //
		};

		short[] indices = new short[] { 0, 1, 2, 0, 2, 3 };

		float[] vertices = new float[] { -0.5f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f,
				0.5f, -0.5f, 0.0f, 0.5f, 0.5f, 0.0f };
		setIndices(indices);
		setVertices(vertices);
		setTextureCoordinates(textureCoordinates);

	}

	@Override
	public void useComp4Renderer(GL10 gl) {
		if(posX > 2) posX = -2f;
		else posX += 0.1;
		// Enabled the vertices buffer for writing and to be used during
		// rendering.
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// Specifies the location and data format of an array of vertex
		// coordinates to use when rendering.
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVerticesBuffer);
		
		
		if(shoudlLoadTexture){
			loadGLTextures(gl);
			shoudlLoadTexture = false;
		}
		
		if (mTextureID != -1 && mTextureBuffer != null) {
			gl.glEnable(GL10.GL_TEXTURE_2D);
			// Enable the texture state
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

			// Point to our buffers
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureID);
		}
		
		gl.glTranslatef(posX, posY, 0);
		
		// Point out the where the color buffer is.
//		gl.glDrawElements(GL10.GL_TRIANGLES, mNumOfIndices,
//				GL10.GL_UNSIGNED_SHORT, mIndicesBuffer);
		((GL11Ext) gl).glDrawTexfOES(posX, posY, 0, bitmap.getWidth(), bitmap.getHeight()); 
		// Disable the vertices buffer.
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

		if (mTextureID != -1 && mTextureBuffer != null) {
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glDisable(GL10.GL_TEXTURE_2D);
		}
	}

	/**
	 * Set the vertices.
	 * 
	 * @param vertices
	 */
	protected void setVertices(float[] vertices) {
		// a float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVerticesBuffer = vbb.asFloatBuffer();
		mVerticesBuffer.put(vertices);
		mVerticesBuffer.position(0);
	}

	/**
	 * Set the indices.
	 * 
	 * @param indices
	 */
	protected void setIndices(short[] indices) {
		// short is 2 bytes, therefore we multiply the number if
		// vertices with 2.
		ByteBuffer ibb = ByteBuffer.allocateDirect(indices.length * 2);
		ibb.order(ByteOrder.nativeOrder());
		mIndicesBuffer = ibb.asShortBuffer();
		mIndicesBuffer.put(indices);
		mIndicesBuffer.position(0);
		mNumOfIndices = indices.length;
	}

	/**
	 * Set the texture coordinates.
	 * 
	 * @param textureCoords
	 */
	protected void setTextureCoordinates(float[] textureCoords) { // New
																	// function.
		// float is 4 bytes, therefore we multiply the number if
		// vertices with 4.
		ByteBuffer byteBuf = ByteBuffer
				.allocateDirect(textureCoords.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mTextureBuffer = byteBuf.asFloatBuffer();
		mTextureBuffer.put(textureCoords);
		mTextureBuffer.position(0);
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

		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
	}
}
