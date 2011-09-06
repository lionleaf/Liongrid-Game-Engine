package com.liongrid.gameengine.view;

public abstract class LViewGroup extends LView implements LViewParent {
	private LView mFocused;
	
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
         if (mFocused != null) {
             mFocused.unFocus();
             mFocused = null;
         }
         super .handleFocusGainInternal(direction);
     }
	 public void requestChildFocus(LView child, LView focused) {
         // Unfocus us, if necessary
         super .unFocus();

         // We had a previous notion of who had focus. Clear it.
         if (mFocused != child) {
             if (mFocused != null) {
                 mFocused.unFocus();
             }

             mFocused = child;
         }
         if (mParent != null) {
             mParent.requestChildFocus(this , focused);
         }
     }
	 
	 public void bringChildToFront(LView child) {
         int index = indexOfChild(child);
         if (index >= 0) {
             removeFromArray(index);
             addInArray(child, mChildrenCount);
             child.mParent = this ;
         }
     }
	 
	 // This method also sets the child's mParent to null
     private void removeFromArray(int index) {
         final LView[] children = mChildren;
         children[index].mParent = null;
         final int count = mChildrenCount;
         if (index == count - 1) {
             children[--mChildrenCount] = null;
         } else if (index >= 0 && index < count) {
             System.arraycopy(children, index + 1, children, index,
                     count - index - 1);
             children[--mChildrenCount] = null;
         } else {
             throw new IndexOutOfBoundsException();
         }
     }

     // This method also sets the children's mParent to null
     private void removeFromArray(int start, int count) {
         final LView[] children = mChildren;
         final int childrenCount = mChildrenCount;

         start = Math.max(0, start);
         final int end = Math.min(childrenCount, start + count);

         if (start == end) {
             return;
         }

         if (end == childrenCount) {
             for (int i = start; i < end; i++) {
                 children[i].mParent = null;
                 children[i] = null;
             }
         } else {
             for (int i = start; i < end; i++) {
                 children[i].mParent = null;
             }

             // Since we're looping above, we might as well do the copy, but is arraycopy()
             // faster than the extra 2 bounds checks we would do in the loop?
             System.arraycopy(children, end, children, start,
                     childrenCount - end);

             for (int i = childrenCount - (end - start); i < childrenCount; i++) {
                 children[i] = null;
             }
         }

         mChildrenCount -= (end - start);
     }
	 
     private void addInArray(LView child, int index) {
         LView[] children = mChildren;
         final int count = mChildrenCount;
         final int size = children.length;
         if (index == count) {
             if (size == count) {
                 mChildren = new LView[size + ARRAY_CAPACITY_INCREMENT];
                 System.arraycopy(children, 0, mChildren, 0, size);
                 children = mChildren;
             }
             children[mChildrenCount++] = child;
         } else if (index < count) {
             if (size == count) {
                 mChildren = new LView[size + ARRAY_CAPACITY_INCREMENT];
                 System.arraycopy(children, 0, mChildren, 0, index);
                 System.arraycopy(children, index, mChildren, index + 1,
                         count - index);
                 children = mChildren;
             } else {
                 System.arraycopy(children, index, children, index + 1,
                         count - index);
             }
             children[index] = child;
             mChildrenCount++;
         } else {
             throw new IndexOutOfBoundsException("index=" + index
                     + " count=" + count);
         }
     }
     
     public void clearChildFocus(LView child) {
         mFocused = null;
         if (mParent != null) {
             mParent.clearChildFocus(this );
         }
     }

     
	 /**
      * Returns the position in the group of the specified child view.
      *
      * @param child the view for which to get the position
      * @return a positive integer representing the position of the view in the
      *         group, or -1 if the view does not exist in the group
      */
     public int indexOfChild(LView child) {
         final int count = mChildrenCount;
         final LView[] children = mChildren;
         for (int i = 0; i < count; i++) {
             if (children[i] == child) {
                 return i;
             }
         }
         return -1;
     }
	 
	 /**
      * Returns the focused child of this view, if any. The child may have focus
      * or contain focus.
      *
      * @return the focused child or null.
      */
     public LView getFocusedChild() {
         return mFocused;
     }
     
     public void focusableViewAvailable(LView v) {
         if (mParent != null && !isFocused()) {
             mParent.focusableViewAvailable(v);
         }
     }
         
     
     
     /**
      * Find the nearest view in the specified direction that wants to take
      * focus.
      *
      * @param focused The view that currently has focus
      * @param direction One of FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, and
      *        FOCUS_RIGHT, or 0 for not applicable.
      */
     /*public LView focusSearch(LView focused, int direction) {
         if (isRootNamespace()) {
             // root namespace means we should consider ourselves the top of the
             // tree for focus searching; otherwise we could be focus searching
             // into other tabs.  see LocalActivityManager and TabHost for more info
             return FocusFinder.getInstance().findNextFocus(this ,
                     focused, direction);
         } else if (mParent != null) {
             return mParent.focusSearch(focused, direction);
         }
         return null;
     }*/

     
}
