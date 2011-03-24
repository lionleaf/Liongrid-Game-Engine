package com.infectosaurus.components;

import javax.microedition.khronos.opengles.GL10;

public abstract class Component{
	abstract public void update4Game(float dt);
	abstract public void update4Renderer(GL10 gl);
}
