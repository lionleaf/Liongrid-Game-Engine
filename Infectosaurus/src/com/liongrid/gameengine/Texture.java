package com.liongrid.gameengine;

/**
 * Simple container class for textures.  Serves as a mapping between Android resource ids and
 * OpenGL texture names, and also as a placeholder object for textures that may or may not have
 * been loaded into vram.  Objects can cache Texture objects but should *never* cache the texture
 * id itself, as it may change at any time.
 */
public class Texture {
	public int resource;
    public int id;
    public int width;
    public int height;
    public boolean loaded = false;
    
    // Variables used for cropping
    public int x = 0;
    public int y = 0;
	public int initialX;
	public int initialY;

    public Texture() {
        super();
        reset();
    }

    public void reset() {
        resource = -1;
        id = -1;
        width = 0;
        height = 0;
        loaded = false;
    }
    
    public void copyTo(Texture tex){
    	tex.resource = resource;
    	tex.id = id;
    	tex.width = width;
    	tex.height = height;
    	tex.loaded = loaded;
    	
    	tex.x = x;
    	tex.y = y;
    	tex.initialX = initialX;
    	tex.initialY = initialY;
    }
}
