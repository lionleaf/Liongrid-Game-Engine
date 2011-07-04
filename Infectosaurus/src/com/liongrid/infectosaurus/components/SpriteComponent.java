package com.liongrid.infectosaurus.components;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.DrawableObject;
import com.liongrid.infectosaurus.InfectoGameObject;

public class SpriteComponent extends Component<InfectoGameObject> {
	public static enum SpriteState{
		idle,
		moving,
		attacking;
	}
	
	SpriteState defaultState = SpriteState.idle; 
	SpriteState currentState = defaultState;
	LAnimation lastAnimation = null;
	DrawableObject lastDrawing = null;
	DrawableObject defaultDrawing;

	LAnimation[] animations = new LAnimation[SpriteState.values().length];
	
	
	public SpriteComponent(){
		
	}
	
	
	public SpriteComponent(DrawableObject drawing){
		this.defaultDrawing = drawing;
	}
	
	
	public void setAnimation(SpriteState s, LAnimation animation){
		animations[s.ordinal()] = animation;
	}
	
	@Override
	public void update(float dt, InfectoGameObject parent){

		float x = parent.pos.x;
		float y = parent.pos.y;
		
		LAnimation animation = animations[currentState.ordinal()];
		DrawableObject toDraw;

		if(animation != null){

			toDraw = animation.getCurrentFrame(dt);
			
			if(toDraw == null && currentState != defaultState){
				currentState = defaultState;
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
		BaseObject.gamePointers.renderSystem.scheduleForDraw(toDraw, x, y, false);
	}
	
}
