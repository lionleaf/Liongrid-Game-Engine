package com.infectosaurus;

import android.graphics.Bitmap;

public class Coordinates {
	private int x;
	private int y;
	private Bitmap bitmap;
	
	Coordinates(Bitmap bitmap){
		this.bitmap = bitmap;
		x = 0; y = 0;
	}
	
	Coordinates(Bitmap bitmap, int x, int y){
		this.bitmap = bitmap;
		setX(x);
		setY(y);
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x + bitmap.getWidth() / 2;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x - bitmap.getWidth() / 2;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y + bitmap.getHeight() / 2;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y - bitmap.getHeight() / 2;;
	}
}
