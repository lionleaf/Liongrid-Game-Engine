package com.liongrid.gameengine;

public interface LionViewParent {
	void bringChildToFront(LionView view);
	void clearChildFocus(LionView view);
	void focusableViewAvailable(LionView view);
	LionView focusSearch(LionView view, int direction);
	LionViewParent getParent();
	void requestChildFocus(LionView child, LionView focused);
}
