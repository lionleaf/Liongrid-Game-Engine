package com.infectosaurus.components;

import android.util.Log;

import com.infectosaurus.BaseObject;
import com.infectosaurus.Drawable;
import com.infectosaurus.GameObject;
import com.infectosaurus.Vector2;

public class SpriteComponent extends Component {
	Drawable drawing;
	Vector2 pos;
	
	public SpriteComponent(Drawable drawing){
		this.drawing = drawing;
		pos = new Vector2();
	}
	
	@Override
	public void update(float dt, BaseObject parent){
		
		GameObject gameObject = (GameObject) parent;
		pos.x = gameObject.posX;
		pos.y = gameObject.posY;
		BaseObject.gamePointers.renderSystem.scheduleForDraw(drawing, pos);
	}
}
