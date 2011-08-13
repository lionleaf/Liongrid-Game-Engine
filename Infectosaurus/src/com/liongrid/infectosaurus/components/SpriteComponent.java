package com.liongrid.infectosaurus.components;

import java.util.HashMap;

import android.util.Log;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LDrawableObject;
import com.liongrid.gameengine.LEasyBitmapCropper;
import com.liongrid.gameengine.LAnimation;
import com.liongrid.infectosaurus.InfectoGameObject;

public class SpriteComponent extends LComponent<InfectoGameObject> {
	 
	private String currentState = null;
	private String currentOverlayAnimation = null;
	private LAnimation lastAnimation = null;
	private LDrawableObject lastDrawing = null;
	private LDrawableObject defaultDrawing;

	//LAnimation[] animations = new LAnimation[SpriteState.values().length];
	
	HashMap<String, LAnimation> animations = new HashMap<String, LAnimation>();
	
	public SpriteComponent(){
		
	}
	
	public void setUnderlyingAnimation(String state){
		currentState = state;
	}
	
	public void setOverlayAnimation(String animationName) {
		currentOverlayAnimation = animationName;
		
	}
	
	public SpriteComponent(LDrawableObject drawing){
		this.defaultDrawing = drawing;
	}
	
	
	public void addAnimation(String state, LAnimation animation){
		if(currentState == null){
			currentState = state;
		}
		animations.put(state, animation);
	}
	
	@Override
	public void update(float dt, InfectoGameObject parent){

		float x = parent.pos.x;
		float y = parent.pos.y;
		
		
		LAnimation animation = null;
		if(currentOverlayAnimation != null){
			animation = animations.get(currentOverlayAnimation);
			assert animation != null;
		}
		
		if(animation == null && currentState != null){
			animation = animations.get(currentState);
		}
		
		LDrawableObject toDraw;

		if(animation != null){

			toDraw = animation.getCurrentFrame(dt);
			
			//OverlayAnimation is over!
			if(toDraw == null){
				if(currentOverlayAnimation == null){
					Log.d("Infectosaurus", "Couldn`t find state in animation hashmap WTF!");
					return;
				}
				currentOverlayAnimation = null;
				//Now that we`ve changed state, recursively try again and return
				update(dt, parent);
				return;
			}
			
			//clean up after ourself
			if(lastAnimation != null && lastAnimation != animation){
				lastAnimation.resetAnimation();
			}
			
			
		}else{
			toDraw = defaultDrawing;
		}
		
		
		//center the image around the pos point
		
		x -= 0.5 * toDraw.getWidth();
		y -= 0.5 * toDraw.getHeight();
		
		lastDrawing = toDraw;
		LBaseObject.gamePointers.renderSystem.scheduleForDraw(toDraw, x, y, false);
	}

	public String getSpriteState() {
		return currentState;
		
	}

	public LDrawableObject getLastDrawing() {
		return lastDrawing;
	}

	
	
}
