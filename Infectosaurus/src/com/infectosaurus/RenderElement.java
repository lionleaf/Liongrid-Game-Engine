package com.infectosaurus;

public class RenderElement extends BaseObject{
	public Drawable drawable;
	//public Vector2 pos;
	public float x;
	public float y;
	
	public RenderElement(){
		super();
	}
		
	@Override
	public void reset() {
		//This data is always set again when reused.
		//So no use in reseting it.
		/*drawable = null;
		x = 0;
		y = 0;*/
	}

	public void set(Drawable object, Vector2 pos) {
		this.drawable = object;
		x = pos.x;
		y = pos.y;
		
	}
	
	public void set(Drawable drawable, int x, int y){
		this.drawable = drawable;
		this.x = x;
		this.y = y;
	}
}