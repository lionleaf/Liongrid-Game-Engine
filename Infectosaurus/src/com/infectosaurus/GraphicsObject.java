package com.infectosaurus;

import android.graphics.Bitmap;

public class GraphicsObject {
	private Coordinates coordinates;
	private Bitmap bitmap;
	
	GraphicsObject(Bitmap bitmap){
		this.bitmap = bitmap;
		coordinates = new Coordinates(bitmap);
	}
	
	/**
	 * @param bitmap the bitmap to set
	 */
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	/**
	 * @return the coordinates
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * @return the bitmap
	 */
	public Bitmap getBitmap() {
		return bitmap;
	}
}
