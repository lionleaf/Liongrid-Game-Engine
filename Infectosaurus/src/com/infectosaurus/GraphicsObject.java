package com.infectosaurus;

import android.graphics.Bitmap;

public class GraphicsObject {
	private Coordinates coordinates;
	private final Bitmap bitmap;
	private final int ID;
	private static int IDcounter = 0;
	
	GraphicsObject(Bitmap bitmap){
		this.bitmap = bitmap;
		this.ID = IDcounter;
		coordinates = new Coordinates(bitmap);
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
