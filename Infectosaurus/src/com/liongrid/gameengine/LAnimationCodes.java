package com.liongrid.gameengine;

public class LAnimationCodes {
	
	public static final String WALK_EAST = "WalkEast";
	public static final String WALK_WEST = "WalkWest";
	public static final String WALK_NORTH = "WalkNorth";
	public static final String WALK_SOUTH = "WalkSouth";
	public static final String WALK_NORTH_EAST = "WalkNorthEast";
	public static final String WALK_NORTH_WEST = "WalkNorthWest";
	public static final String WALK_SOUTH_EAST = "WalkSouthEast";
	public static final String WALK_SOUTH_WEST = "WalkSouthWest";
	public static final String SPAWNING = "Spawning";
	public static final String ATTACK_EAST = "AttackEast";
	
	public static String getWalk8Directions(float dx, float dy){
		
		final float absAB = 
			(float) Math.sqrt(dx * dx + dy * dy);
		final float cosAB = dx / absAB;
		final float sinAB = dy / absAB;
		
		
		boolean facingUp = sinAB > 0 ? true : false;
		
		String result = WALK_SOUTH;
		
		final float cos22Deg = 0.92388f;
		final float cos67Deg = 0.38268f;
		final float cos112Deg = - cos67Deg;
		final float cos157Deg = - cos22Deg;
		final float cos180Deg = -1;
			
		if(cosAB > cos22Deg){
			result = WALK_EAST;
		}else if(cosAB > cos67Deg){
			result = facingUp ? WALK_NORTH_EAST : WALK_SOUTH_EAST; 
		}else if(cosAB > cos112Deg){
			result = facingUp ? WALK_NORTH : WALK_SOUTH;
		}else if(cosAB > cos157Deg){
			result = facingUp ? WALK_NORTH_WEST : WALK_SOUTH_WEST;
		}else if(cosAB >= cos180Deg){
			result = WALK_NORTH_WEST;
		}
		
		return result;
	}

}
