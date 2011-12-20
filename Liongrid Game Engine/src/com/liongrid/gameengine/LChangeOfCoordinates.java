package com.liongrid.gameengine;

import com.liongrid.gameengine.tools.*;

public class LChangeOfCoordinates {
	
	public static void fromIsoToOrth(LVector2 pos, LVector2 copyTo){
		final float x = fromIsoToOrthX(pos.x, pos.y);
		final float y = fromIsoToOrthY(pos.x, pos.y);
		copyTo.set(x, y);
	}
	
	public static void fromOrthToIso(LVector2 pos, LVector2 copyTo){
		final float x = fromOrthToIsoX(pos.x, pos.y);
		final float y = fromOrthToIsoY(pos.x, pos.y);
		copyTo.set(x, y);
	}
	
	public static float fromOrthToIsoX(float x, float y){
		return 0;
	}
	
	public static float fromOrthToIsoY(float x, float y){
		return 0;
	}

	public static float fromIsoToOrthX(float x, float y){
		return 0;
	}
	
	public static float fromIsoToOrthY(float x, float y){
		return 0;
	}
}
