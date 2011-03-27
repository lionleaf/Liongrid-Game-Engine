package com.infectosaurus;

public class RenderElement extends BaseObject{
	public Drawable drawable;
	//public Vector2 pos;
	public float x;
	public float y;
	
	public RenderElement(Drawable object, Vector2 pos){
		this.drawable = object;
		x = pos.x;
		y = pos.y;
	}

	public RenderElement(DrawableBitmap drawBitmap, int x, int y) {
		this.drawable = drawBitmap;
		this.x = x;
		this.y = y;
	}
		
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}