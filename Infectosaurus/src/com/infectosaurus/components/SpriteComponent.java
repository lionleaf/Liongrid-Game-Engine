package com.infectosaurus.components;

import com.infectosaurus.BaseObject;
import com.infectosaurus.Drawable;
import com.infectosaurus.GameObject;
import com.infectosaurus.Tile;
import com.infectosaurus.Vector2;

public class SpriteComponent extends Component {
	Drawable drawing;
	Vector2 pos;
	Vector2 lastPos;
	Tile tile;
	
	public SpriteComponent(Drawable drawing){
		this.drawing = drawing;
		pos = new Vector2();
		tile = BaseObject.gamePointers.tileSystem;
	}
	
	@Override
	public void update(float dt, BaseObject parent){
		GameObject gameObject = (GameObject) parent;
		lastPos = pos;
		pos.x = gameObject.posX;
		pos.y = gameObject.posY;
		BaseObject.gamePointers.renderSystem.scheduleForDraw(drawing, pos);
		clearPreviousTile(lastPos);
	}
	
	private void clearPreviousTile(Vector2 vec){
		tile.clearArea((int)vec.x,(int)vec.y,drawing.getWidth(),drawing.getHeight());
	}
}
