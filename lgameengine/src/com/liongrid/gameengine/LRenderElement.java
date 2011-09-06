package com.liongrid.gameengine;

import java.util.Comparator;

import com.liongrid.gameengine.tools.LVector2;

public class LRenderElement extends LBaseObject{
	public LDrawableObject drawable;
	//public LVector2 pos;
	public float x;
	public float y;
	public boolean cameraRelative;
	public float scale;
	
	static class HeightComparer implements Comparator<LRenderElement>{

		public int compare(LRenderElement object1, LRenderElement object2) {
			float y1 = object1.y;
			float y2 = object2.y;
			return  -Float.compare(y1, y2);
		}
		
	}
	
	static HeightComparer comparer = new HeightComparer();
	
	
	public LRenderElement(){
		super();
	}
		
	@Override
	public void reset() {
		//This data is always set again when reused.
		//So no use in reseting it.
		drawable = null;
		x = 0;
		y = 0;
		scale = 1f;
		cameraRelative = false;
	}

	public void set(LDrawableObject object, LVector2 pos, boolean cameraRelative) {
		this.drawable = object;
		x = pos.x;
		y = pos.y;
		this.cameraRelative = cameraRelative;
	}
	
	public void set(LDrawableObject drawable, int x, int y, boolean cameraRelative){
		this.drawable = drawable;
		this.x = x;
		this.y = y;
		this.cameraRelative = cameraRelative;
	}

	@Override
	public void update(float dt, LBaseObject parent) {
		// TODO Auto-generated method stub
		
	}
}