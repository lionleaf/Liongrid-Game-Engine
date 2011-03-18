package com.infectosaurus;

public interface Creature {
	int 	getHealth();
	boolean decreaseHealth();
	int 	getDMGRed();
	void 	setDMGRed();
	int 	getSize();
}
