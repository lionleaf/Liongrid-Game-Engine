package com.infectosaurus;

import android.graphics.Bitmap;

public abstract class AbsCreature extends GraphicsObject implements Creature{

	AbsCreature(Bitmap bitmap) {
		super(bitmap);
	}

	@Override
	public int getHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean decreaseHealth() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getDMGRed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDMGRed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
