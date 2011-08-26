package com.liongrid.gameengine;

public interface LViewParent {
	void bringChildToFront(LView view);
	void clearChildFocus(LView view);
	void focusableViewAvailable(LView view);
	LView focusSearch(LView view, int direction);
	LViewParent getParent();
	void requestChildFocus(LView child, LView focused);
}
