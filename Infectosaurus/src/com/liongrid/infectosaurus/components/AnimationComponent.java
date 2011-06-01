package com.liongrid.infectosaurus.components;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.DrawableObject;
import com.liongrid.gameengine.tools.Vector2;
import com.liongrid.infectosaurus.InfectoGameObject;

public class AnimationComponent extends Component<InfectoGameObject> {
	DrawableObject[] drawings;
	int frames;
	Vector2 pos;
	Vector2 lastPos;
	private float spf;
	float timePassed = 0;
	
	/**
	 * @param drawings - an array of frames
	 * @param mspf - seconds per frame
	 */
	public AnimationComponent(DrawableObject[] drawings, float spf){
		
		this.drawings = drawings;
		frames = drawings.length;
		this.spf = spf;
		lastPos = new Vector2();
	}
	
	@Override
	public void update(float dt, InfectoGameObject parent){
		timePassed += dt;
		
		int drawableIndex =(int) (timePassed / spf); //This should round down
		
		if(drawableIndex >= frames){
			timePassed = 0;
			drawableIndex = 0;
		}
		
		pos = parent.pos;
		lastPos.set(pos);
		DrawableObject drawing = drawings[drawableIndex];
		BaseObject.gamePointers.renderSystem.scheduleForDraw(drawing, pos);
	}
}
