package com.liongrid.thumbfighter.components;

import com.liongrid.gameengine.LComponent;
import com.liongrid.gameengine.LGameObject;
import com.liongrid.gameengine.LGamePointers;

public class TRemoveOutsideComponent extends LComponent {

	@Override
	public void update(float dt, LGameObject parent) {
		if(parent.pos.y < -256 || parent.pos.y > LGamePointers.panel.getHeight()){
			LGamePointers.root.remove(parent);
		}
		
	}

}
