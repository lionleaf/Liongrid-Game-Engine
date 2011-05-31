package com.liongrid.infectosaurus.components;

import android.util.Log;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Component;
import com.liongrid.gameengine.DrawableObject;
import com.liongrid.gameengine.GameObject;
import com.liongrid.infectosaurus.InfectoGameObject;
import com.liongrid.infectosaurus.map.Level;
import com.liongrid.infectosaurus.tools.Vector2;

public class SpriteComponent extends Component<InfectoGameObject> {
	DrawableObject drawing;
	Vector2 pos;
	Vector2 lastPos;
	
	public SpriteComponent(DrawableObject drawing){
		
		this.drawing = drawing;
		lastPos = new Vector2();
	}
	
	@Override
	public void update(float dt, InfectoGameObject parent){

		pos = parent.pos;
		lastPos.set(pos);
			
		BaseObject.gamePointers.renderSystem.scheduleForDraw(drawing, pos);
	}
	
}