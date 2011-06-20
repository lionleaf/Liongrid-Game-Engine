package com.liongrid.infectosaurus.components;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.DrawableObject;
import com.liongrid.gameengine.Texture;
import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.InfectoGameObject;

/**
 * @author Lionleaf
 *	Too many classes are named Animation, the L stands for Liongrid
 *	
 *	Stores an animation with a fixed fps and number of frames.
 *
 */
public class LAnimation extends BaseObject {
	DrawableObject[] drawings;
	int frames;
	Vector2 pos;
	private float spf;
	float timePassed = 0;
	private boolean repeat = true;
	
	
	//Has to have an empty one in case it should be pooled
	public LAnimation(){
		
	}
	
	/**
	 * @param drawings - an array of frames
	 * @param mspf - seconds per frame
	 */
	public LAnimation(DrawableObject[] drawings, float spf){
		set(drawings, spf);
	}
	
	public LAnimation(DrawableBitmap[] attackBmps, float spf, boolean repeat) {
			this(attackBmps, spf);
			this.repeat  = repeat;
	}

	/**
	 * @param drawings - an array of frames
	 * @param mspf - seconds per frame
	 */
	public void set(DrawableObject[] drawings, float spf){
		
		this.drawings = drawings;
		frames = drawings.length;
		this.spf = spf;
	}
	
	/**
	 * @param dt gametime since last call (in sec)
	 * @return the current frame to be drawn. null if the animation is over.
	 */
	public DrawableObject getCurrentFrame(float dt){
		timePassed += dt;
		
		int drawableIndex =(int) (timePassed / spf); //This should round down
		
		if(drawableIndex >= frames){
			timePassed = 0;
			drawableIndex = 0;
			if(!repeat){
				return null;
			}
		}
		 
		return drawings[drawableIndex];
	}

	public void resetAnimation(){
		timePassed = 0;
	}
	
	@Override
	public void reset() {
		spf = 1;
		drawings = null;
		frames = 0;
		timePassed = 0;
	}

	@Override
	public void update(float dt, BaseObject parent) {
		
	}
}
