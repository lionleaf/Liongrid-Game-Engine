package com.infectosaurus;

import com.infectosaurus.components.AggressivMoveComponent;
import com.infectosaurus.components.MeleeAttackComponent;
import com.infectosaurus.components.MoveComponent;
import com.infectosaurus.components.RandomWalkerComponent;
import com.infectosaurus.components.SpriteComponent;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Infectosaurus extends GameObject {
	Infectosaurus() {
		Log.d(Main.TAG, "Infectosaurus construct");
		Panel panel = BaseObject.gamePointers.panel;
		
		addComponent(new MeleeAttackComponent());
		
		DrawableBitmap db = new DrawableBitmap(
				R.drawable.lumberinghulklo,16,16,panel.getContext());
		addComponent(new AggressivMoveComponent());
		addComponent(new SpriteComponent(db));
		addComponent(new MoveComponent());
		speed = 100;
		team = Team.Alien;
	}
}
