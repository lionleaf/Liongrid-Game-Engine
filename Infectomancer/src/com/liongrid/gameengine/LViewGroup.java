package com.liongrid.gameengine;

public abstract class LViewGroup extends LView {
	private LView mFocusedView;
	
	// Child views of this ViewGroup
	private LView[] mChildren;
	
	// Number of valid children in the mChildren array, the rest should be null or not
    // considered as children
	private int mChildrenCount;
	
	private static final int ARRAY_INITIAL_CAPACITY = 12;
    private static final int ARRAY_CAPACITY_INCREMENT = 12;
    
    public LViewGroup() {
        super();
        initViewGroup();
    }

    
	private void initViewGroup() {
		mChildren = new LView[ARRAY_INITIAL_CAPACITY];
        mChildrenCount = 0;		
	}
	
	 @Override
	protected void handleFocusGainInternal(int direction) {
         if (mFocusedView != null) {
             mFocusedView.unFocus();
             mFocusedView = null;
         }
         super .handleFocusGainInternal(direction);
     }
	 public void requestChildFocus(LView child, LView focused) {
         // Unfocus us, if necessary
         super .unFocus();

         // We had a previous notion of who had focus. Clear it.
         if (mFocusedView != child) {
             if (mFocusedView != null) {
                 mFocusedView.unFocus();
             }

             mFocusedView = child;
         }
         if (mParent != null) {
             mParent.requestChildFocus(this , focused);
         }
     }
	 
	 /**
      * Returns the focused child of this view, if any. The child may have focus
      * or contain focus.
      *
      * @return the focused child or null.
      */
     public LView getFocusedChild() {
         return mFocusedView;
     }
     
}
