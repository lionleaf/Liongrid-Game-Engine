package com.infectosaurus;

import android.graphics.Bitmap;

public class GraphicsObject {
	private Coordinates coordinates;
	private Bitmap bitmap;
	private static int IDcounter = 0;
	private int ID;
	
	GraphicsObject(Bitmap bitmap){
		this.bitmap = bitmap;
		coordinates = new Coordinates(bitmap);
		this.ID = IDcounter;
		IDcounter++;
	}
	
	GraphicsObject(Bitmap bitmap, int ID){
		this.bitmap = bitmap;
		//This is a problem if the id is less than instanceNumber
		coordinates = new Coordinates(bitmap);
		this.ID = ID;
		
	}
	
	/**
	 * @return the id
	 */
	public int getID() {
		return ID;
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
