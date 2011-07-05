package com.liongrid.infectosaurus;

import com.liongrid.gameengine.ObjectHandler;
import com.liongrid.infectosaurus.hudobjects.HUDObject;
import com.liongrid.infectosaurus.hudobjects.HUDScore;

public class HUDObjectHandler extends ObjectHandler<HUDObject>{

	public HUDObjectHandler() {
		add(new HUDScore());
	}
}
