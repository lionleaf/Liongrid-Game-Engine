package com.infectosaurus;

import javax.microedition.khronos.opengles.GL10;

public interface Drawable {
	public void draw(GL10 gl, float x, float y, float scaleX, float scaleY);
	int getWidth();
	int getHeight();
}
