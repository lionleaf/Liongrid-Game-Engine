package com.infectosaurus.crowd.situations;

import com.infectosaurus.GameObject;

public abstract class Situation {
	Situation(GameObject go) {
		applySituation(go);
	}

	abstract void applySituation(GameObject go);
		
}
