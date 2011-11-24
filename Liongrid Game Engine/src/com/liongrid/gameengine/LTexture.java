package com.liongrid.gameengine;

/**
 * Simple container class for textures.  Serves as a mapping between Android resource ids and
 * OpenGL texture names, and also as a placeholder object for textures that may or may not have
 * been loaded into vram.  Objects can cache LTexture objects but should *never* cache the texture
 * id itself, as it may change at any time.
 */
public class LTexture {
	public int resource;
    public int id;
    public int width;
    public int height;
    public boolean loaded = false;
	public int[] defaultCrop;
    
    public LTexture() {
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
    
    public void copyTo(LTexture tex){
    	tex.resource = resource;
    	tex.id = id;
    	tex.width = width;
    	tex.height = height;
    	tex.loaded = loaded;
    }
}
