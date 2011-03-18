package com.infectosaurus;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class Infectosaurus extends AbsCreature implements Attack {

	Infectosaurus(Resources res) {
		super(BitmapFactory.decodeResource(res, R.drawable.lumberinghulklo));
	}

	@Override
	public void useMeleeAttack(Creature creature) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasMeleeAttack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void useRangedAttack(Creature creature) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasRangedAttack() {
		// TODO Auto-generated method stub
		return false;
	}

}
