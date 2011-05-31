package com.liongrid.gameengine.tools;


public class Vector2Int {
	    public int x;
	    public int y;

	    public static final Vector2Int ZERO = new Vector2Int(0, 0);

	    public Vector2Int() {
	        super();
	    }

	    public Vector2Int(int xValue, int yValue) {
	        set(xValue, yValue);
	    }

	    public Vector2Int(Vector2Int other) {
	        set(other);
	    }
	    
	    public String toString(){
	    	return "("+x+","+y+")";
	    }
	    
	    public final void add(Vector2Int other) {
	        x += other.x;
	        y += other.y;
	    }

	    public final void add(int otherX, int otherY) {
	        x += otherX;
	        y += otherY;
	    }

	    public final void subtract(Vector2Int other) {
	        x -= other.x;
	        y -= other.y;
	    }

	    public final void multiply(int magnitude) {
	        x *= magnitude;
	        y *= magnitude;
	    }

	    public final void multiply(Vector2Int other) {
	        x *= other.x;
	        y *= other.y;
	    }

	    public final void divide(int magnitude) {
	        if (magnitude != 0) {
	            x /= magnitude;
	            y /= magnitude;
	        }
	    }

	    public final void set(Vector2Int other) {
	        x = other.x;
	        y = other.y;
	    }

	    public final void set(int xValue, int yValue) {
	        x = xValue;
	        y = yValue;
	    }

	    public final float dot(Vector2 other) {
	        return (x * other.x) + (y * other.y);
	    }

	    public final int length() {
	        return (int) Math.sqrt(length2());
	    }

	    public final int length2() {
	        return (x * x) + (y * y);
	    }

	    public final int distance2(Vector2Int other) {
	        int dx = x - other.x;
	        int dy = y - other.y;
	        return (dx * dx) + (dy * dy);
	    }

	    public final int normalize() {
	        final int magnitude = length();

	        // TODO: I'm choosing safety over speed here.
	        if (magnitude != 0) {
	            x /= magnitude;
	            y /= magnitude;
	        }

	        return magnitude;
	    }

	    public final void zero() {
	        set(0, 0);
	    }

	    public final void flipHorizontal(int aboutWidth) {
	        x = (aboutWidth - x);
	    }

	    public final void flipVertical(int aboutHeight) {
	        y = (aboutHeight - y);
	    }
}
