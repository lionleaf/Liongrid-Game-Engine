package com.infectosaurus;

import javax.microedition.khronos.opengles.GL10;

public interface Drawable {
	public void draw(GL10 gl, float x, float y, float scaleX, float scaleY);
	public void setPriority(float f);
	public void getPriority(float f); 
}
