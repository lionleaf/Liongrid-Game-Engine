package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.LVector2;

/**
 * @author Lionleaf
 *	Too many classes are named Animation, the L stands for Liongrid
 *	
 *	Stores an animation with a fixed fps and number of frames.
 *
 */
public class LAnimation extends LBaseObject {
	LDrawableObject[] drawings;
	int frames;
	LVector2 pos;
	private float spf;
	float timePassed = 0;
	private boolean repeat = true;
	
	
	//Has to have an empty one in case it should be pooled
	public LAnimation(){
		
	}
	
	/**
	 * @param drawings - an array of frames
	 * @param mspf - seconds per frame
	 * @param repeat - Should the animation loop?
	 */
	public LAnimation(LDrawableBitmap[] drawings, float spf, boolean repeat) {
			set(drawings, spf, repeat);
			this.repeat  = repeat;
	}

	/**
	 * @param drawings - an array of frames
	 * @param mspf - seconds per frame
	 */
	public void set(LDrawableObject[] drawings, float spf, boolean repeat){
		this.repeat = repeat;
		this.drawings = drawings;
		this.frames = drawings.length;
		this.spf = spf;
	}
	
	/**
	 * @param dt gametime since last call (in sec)
	 * @return the current frame to be drawn. null if the animation is over.
	 */
	public LDrawableObject getCurrentFrame(float dt){
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
	public void update(float dt, LBaseObject parent) {
		
	}
}
