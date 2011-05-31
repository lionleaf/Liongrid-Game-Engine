package com.liongrid.gameengine;

import java.util.Comparator;

import com.liongrid.infectosaurus.tools.Vector2;

import android.util.Log;

public class RenderElement extends BaseObject{
	public DrawableObject drawable;
	//public Vector2 pos;
	public float x;
	public float y;
	
	static class HeightComparer implements Comparator<RenderElement>{

		public int compare(RenderElement object1, RenderElement object2) {
			float y1 = object1.y;
			float y2 = object2.y;
			return  -Float.compare(y1, y2);
		}
		
	}
	
	static HeightComparer comparer = new HeightComparer();
	
	
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

	public void set(DrawableObject object, Vector2 pos) {
		this.drawable = object;
		x = pos.x;
		y = pos.y;
		
	}
	
	public void set(DrawableObject drawable, int x, int y){
		this.drawable = drawable;
		this.x = x;
		this.y = y;
	}

	@Override
	public void update(float dt, BaseObject parent) {
		// TODO Auto-generated method stub
		
	}
}