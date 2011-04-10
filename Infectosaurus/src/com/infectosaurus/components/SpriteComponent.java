package com.infectosaurus.components;

import com.infectosaurus.BaseObject;
import com.infectosaurus.Drawable;
import com.infectosaurus.GameObject;
import com.infectosaurus.TileManager;
import com.infectosaurus.Vector2;

public class SpriteComponent extends Component {
	Drawable drawing;
	Vector2 pos;
	Vector2 lastPos;
	TileManager tile;
	
	public SpriteComponent(Drawable drawing){
		
		this.drawing = drawing;
		lastPos = new Vector2();
		tile = BaseObject.gamePointers.tileSystem;
	}
	
	@Override
	public void update(float dt, BaseObject parent){
		GameObject gameObject = (GameObject) parent;
		pos = gameObject.pos;
		lastPos.set(pos);
		
		BaseObject.gamePointers.renderSystem.scheduleForDraw(drawing, pos);
		clearPreviousTile(lastPos);
	}
	
	private void clearPreviousTile(Vector2 vec){
		tile.clearArea((int)vec.x,(int)vec.y, drawing.getWidth(), drawing.getHeight());
	}
}
