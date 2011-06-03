package com.liongrid.infectosaurus.components;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.DrawableObject;
import com.liongrid.gameengine.GameObject;
import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.components.SpriteComponent.SpriteState;
import com.liongrid.infectosaurus.map.Level;

public class SpriteComponent extends Component<InfectoGameObject> {
	public static enum SpriteState{
		idle,
		moving,
		attacking;
	}
	
	SpriteState defaultState = SpriteState.idle; 
	SpriteState currentState = defaultState;
	LAnimation lastAnimation = null;
	DrawableObject drawing;
	Vector2 pos;

	LAnimation[] animations = new LAnimation[SpriteState.values().length];
	
	
	public SpriteComponent(){
		
	}
	
	
	public SpriteComponent(DrawableObject drawing){
		this.drawing = drawing;
	}
	
	
	public void setAnimation(SpriteState s, LAnimation animation){
		animations[s.ordinal()] = animation;
	}
	
	@Override
	public void update(float dt, InfectoGameObject parent){

		pos = parent.pos;
		
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
			toDraw = drawing;
		}
		
		
		//center the image around the pos point
		
		pos.x -= 0.5 * drawing.getWidth();
		pos.y  -= 0.5 * drawing.getHeight();
		
		BaseObject.gamePointers.renderSystem.scheduleForDraw(toDraw, pos);
	}
	
}
