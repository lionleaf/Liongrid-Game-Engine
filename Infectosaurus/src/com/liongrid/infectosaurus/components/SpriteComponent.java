package com.liongrid.infectosaurus.components;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Drawable;
import com.liongrid.gameengine.GameObject;
import com.liongrid.infectosaurus.map.Level;
import com.liongrid.infectosaurus.tools.Vector2;

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
