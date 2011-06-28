package com.liongrid.infectosaurus;

import com.liongrid.gameengine.Upgrade;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;

/**
 * @author Liongrid
 *	
 *	This is a talent tree. It`s has common attributes with radiogroup
 *	and TableLayout. It`s children should be TalentTier whose children again
 *	should be TalentIcon 
 *
 */
public class TalentTree extends TableLayout {

	private int mSelectedId = -1;
	private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
	private OnSelectedChangeListener mOnSelectedChangeListener;
	private PassThroughHierarchyChangeListener mPassThroughListener;
	/**
	 * Used to avoid infinite recursions.
	 */
	private boolean mProtectFromCheckedChange;


	public TalentTree(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void init(){
		mChildOnCheckedChangeListener = new CheckedStateTracker();
		mPassThroughListener = new PassThroughHierarchyChangeListener();
		super.setOnHierarchyChangeListener(mPassThroughListener);
	}
	
	@Override
	public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
		// the user listener is delegated to our pass-through listener
		mPassThroughListener.mOnHierarchyChangeListener = listener;
	}
	
	public void upgradeCurrent(){

	}


	@Override
	public void addView(View child) {
		viewAdded(child);

		super.addView(child);
	}

	@Override
	public void addView(View child, int width, int height) {
		viewAdded(child);

		super.addView(child, width, height);
	}

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		viewAdded(child);

		super.addView(child, index, params);
	}


	private void viewAdded(View child){
		
		if(child instanceof TalentTier){
			ViewGroup childGroup = ((ViewGroup) child);
			int childCount = childGroup.getChildCount();
			for (int i = 0; i < childCount; i++) {
				View grandChild = childGroup.getChildAt(i);
				if(grandChild instanceof TalentIcon){
					addIcon((TalentIcon)grandChild);
				}
			}
		}
		
	}
	
	private void addIcon(TalentIcon icon){		
		int id = icon.getId();
		// generates an id if it's missing
		if (id == View.NO_ID) {
			id = icon.hashCode();
			icon.setId(id);
		}
		
		if (icon.isChecked()) {
			mProtectFromCheckedChange = true;
			if (mSelectedId != -1) {
				setCheckedStateForView(mSelectedId, false);
			}
			mProtectFromCheckedChange = false;
			setSelectedId(icon.getId());
		}
		
		icon.setOnCheckedChangeListener(mChildOnCheckedChangeListener);
	}
	
	

	public int getSelectedId(){
		return mSelectedId;
	}

	public interface OnSelectedChangeListener{

		/**
		 * Called when another talent is selected in the TalentTree
		 * 
		 * @param tTree - The tree where the selection changed
		 * @param selectedId - the unique id of the new selection 
		 * 	(for findViewById() use)
		 */
		public void onSelectedChanged(TalentTree tTree, int selectedId);
	}

	private class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener{

		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			//Avoid infinite recursion
			if(mProtectFromCheckedChange){
				return;
			}

			mProtectFromCheckedChange = true;
			if(mSelectedId != -1){
				setCheckedStateForView(mSelectedId, false);
			}
			mProtectFromCheckedChange = false;

			int id = buttonView.getId();
			setSelectedId(id);
		}



	}
	
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		// checks the appropriate radio button as requested in the XML file
        if (mSelectedId != -1) {
            mProtectFromCheckedChange = true;
            setCheckedStateForView(mSelectedId, true);
            mProtectFromCheckedChange = false;
            setSelectedId(mSelectedId);
        }
		
	}

	private void setCheckedStateForView(int viewId, boolean checked) {
		View checkedView = findViewById(viewId);
		if (checkedView != null && checkedView instanceof TalentIcon) {
			((TalentIcon) checkedView).setChecked(checked);
		}
	}

	private void setSelectedId(int id) {
		mSelectedId = id;
		if(mOnSelectedChangeListener != null){
			mOnSelectedChangeListener.onSelectedChanged(this, id);
		}
	}

	/**
	 * <p>Register a callback to be invoked when the selected talent
	 * changes in this tree.</p>
	 *
	 * @param listener the callback to call on selected state change
	 */
	public void setOnSelectedChangeListener(OnSelectedChangeListener listener) {
		mOnSelectedChangeListener = listener;
	}

	

	/**
	 * <p>A pass-through listener acts upon the events and dispatches them
	 * to another listener. This allows the table layout to set its own internal
	 * hierarchy change listener without preventing the user to setup his.</p>
	 */
	private class PassThroughHierarchyChangeListener implements
	ViewGroup.OnHierarchyChangeListener {
		private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

		/**
		 * {@inheritDoc}
		 */
		public void onChildViewAdded(View parent, View child) {			
			if (mOnHierarchyChangeListener != null) {
				mOnHierarchyChangeListener.onChildViewAdded(parent, child);
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public void onChildViewRemoved(View parent, View child) {
			if (parent.getParent() == TalentTree.this && 
					child instanceof TalentIcon) {
				((TalentIcon) child).setOnCheckedChangeListener(null);
			}

			if (mOnHierarchyChangeListener != null) {
				mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
			}
		}
	}

}
