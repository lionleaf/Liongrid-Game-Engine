package com.infectosaurus.components;

import android.util.Log;

import com.infectosaurus.BaseObject;
import com.infectosaurus.Drawable;
import com.infectosaurus.GameObject;
import com.infectosaurus.map.Level;
import com.infectosaurus.tools.Vector2;

public class SpriteComponent extends Component {
	Drawable drawing;
	Vector2 pos;
	Vector2 lastPos;
	
	public SpriteComponent(Drawable drawing){
		
		this.drawing = drawing;
		lastPos = new Vector2();
	}
	
	@Override
	public void update(float dt, BaseObject parent){
		GameObject gameObject = (GameObject) parent;
		pos = gameObject.pos;
		lastPos.set(pos);
			
		BaseObject.gamePointers.renderSystem.scheduleForDraw(drawing, pos);
	}
	
}
