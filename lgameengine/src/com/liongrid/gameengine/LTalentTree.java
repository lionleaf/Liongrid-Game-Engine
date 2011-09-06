//package com.liongrid.gameengine;
//
//import com.liongrid.infectomancer.R;
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CompoundButton;
//import android.widget.TableLayout;
//
///**
// * @author Liongrid
// *	
// *	This is a talent tree. It`s has common attributes with radiogroup
// *	and TableLayout. It`s children should be LTalentTier whose children again
// *	should be LTalentIcon 
// *
// */
//public class LTalentTree extends TableLayout {
//
//	private int mSelectedId = -1;
//	private CompoundButton.OnCheckedChangeListener mChildOnCheckedChangeListener;
//
//	private OnSelectedChangeListener mOnSelectedChangeListener;
//
//	private RankChangeListener mRankChangeListener;
//	
//	/**
//	 * Used to avoid infinite recursions.
//	 */
//	private boolean mProtectFromCheckedChange;
//	
//	private int mRanksPerTier = 5;
//
//
//	public LTalentTree(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		init(attrs);
//	}
//
//	private void addIcon(LTalentIcon icon){		
//		int id = icon.getId();
//		// generates an id if it's missing
//		if (id == View.NO_ID) {
//			id = icon.hashCode();
//			icon.setId(id);
//		}
//		
//		if (icon.isChecked()) {
//			mProtectFromCheckedChange = true;
//			if (mSelectedId != -1) {
//				setCheckedStateForView(mSelectedId, false);
//			}
//			mProtectFromCheckedChange = false;
//			setSelectedId(id);
//		}
//		
//		icon.setOnRankChangedListener(mRankChangeListener);
//		icon.setOnCheckedChangeListener(mChildOnCheckedChangeListener);
//	}
//
//	@Override
//	public void addView(View child) {
//		viewAdded(child);
//
//		super.addView(child);
//	}
//
//
//	@Override
//	public void addView(View child, int width, int height) {
//		viewAdded(child);
//
//		super.addView(child, width, height);
//	}
//	
//	@Override
//	public void addView(View child, int index, ViewGroup.LayoutParams params) {
//		viewAdded(child);
//
//		super.addView(child, index, params);
//	}
//	
//	
//
//	public int getSelectedId(){
//		return mSelectedId;
//	}
//
//	public void init(AttributeSet attrs){
//		mChildOnCheckedChangeListener = new CheckedStateTracker();
//
//		mRankChangeListener = new RankChangeListener();
//		
//		TypedArray a = 
//			getContext().obtainStyledAttributes(attrs,R.styleable.TalentTree);
//		
//		int xmlRanksPerTier = a.getInteger(R.styleable.TalentTree_ranks_per_tier, -1);
//		if(xmlRanksPerTier >= 0){
//			mRanksPerTier = xmlRanksPerTier;
//		}
//		
//		
//
//	}
//
//	@Override
//	protected void onFinishInflate() {
//		super.onFinishInflate();
//		
//		/*
//        if (mSelectedId != -1) {
//            mProtectFromCheckedChange = true;
//            setCheckedStateForView(mSelectedId, true);
//            mProtectFromCheckedChange = false;
//            setSelectedId(mSelectedId);
//        }
//        */
//        
//        updateUpgradabilityStates();
//		
//	}
//	
//	private void setCheckedStateForView(int viewId, boolean checked) {
//		View checkedView = findViewById(viewId);
//		if (checkedView != null && checkedView instanceof LTalentIcon) {
//			((LTalentIcon) checkedView).setChecked(checked);
//		}
//	}
//
//	/**
//	 * <p>Register a callback to be invoked when the selected talent
//	 * changes in this tree.</p>
//	 *
//	 * @param listener the callback to call on selected state change
//	 */
//	public void setOnSelectedChangeListener(OnSelectedChangeListener listener) {
//		mOnSelectedChangeListener = listener;
//	}
//
//	private void setSelectedId(int id) {
//		mSelectedId = id;
//		if(mOnSelectedChangeListener != null){
//			mOnSelectedChangeListener.onSelectedChanged(this, id);
//		}
//	}
//
//	private void updateUpgradabilityStates(){
//		// First implementation uses "Brute force"
//		
//		boolean upgradeable = true;
//		int totalRank = 0;
//		int currentTier = 0;
//		
//		int nrOfChildren = getChildCount();
//		for (int i = 0; i < nrOfChildren; i++) {
//			currentTier++;
//			
//			View child = getChildAt(i);
//			if(child instanceof ViewGroup){
//				ViewGroup childGroup = (ViewGroup) child;
//				int nrOfGChildren = childGroup.getChildCount();
//				for (int j = 0; j < nrOfGChildren; j++) {
//					View grandChild = childGroup.getChildAt(j);
//					if(grandChild instanceof LTalentIcon){
//						((LTalentIcon) grandChild).setUpgradeable(upgradeable);
//						if(!upgradeable) continue;
//						int rank = ((LTalentIcon) grandChild).getUpgrade().getRank();
//						totalRank += rank;
//					}
//				}
//				
//				//Set whether next tier is upgradeable;
//				upgradeable = upgradeable ? 
//						totalRank >= mRanksPerTier*currentTier : false;
//				
//			}
//		}
//	}
//	
//	private void viewAdded(View child){
//		if(child instanceof LTalentTier){
//			
//			ViewGroup childGroup = ((ViewGroup) child);
//			int childCount = childGroup.getChildCount();
//			Log.d("LTalentTree", "View added, children: "+childCount);
//			for (int i = 0; i < childCount; i++) {
//				View grandChild = childGroup.getChildAt(i);
//				if(grandChild instanceof LTalentIcon){
//					addIcon((LTalentIcon)grandChild);
//				}
//			}
//		}
//		
//	}
//	
//
//	private class CheckedStateTracker implements CompoundButton.OnCheckedChangeListener{
//
//		public void onCheckedChanged(CompoundButton buttonView,
//				boolean isChecked) {
//			
//			
//			//Avoid infinite recursion
//			if(mProtectFromCheckedChange){
//				return;
//			}
//			Log.d("LTalentTree", "onCheckChanged "+buttonView.getId());
//			int id = buttonView.getId();
//			
//			mProtectFromCheckedChange = true;
//			if(mSelectedId != -1 && mSelectedId != id){
//				setCheckedStateForView(mSelectedId, false);
//			}
//			mProtectFromCheckedChange = false;
//
//			
//			setSelectedId(id);
//		}
//
//
//
//	}
//	public interface OnSelectedChangeListener{
//
//		/**
//		 * Called when another talent is selected in the LTalentTree
//		 * 
//		 * @param tTree - The tree where the selection changed
//		 * @param selectedId - the unique id of the new selection 
//		 * 	(for findViewById() use)
//		 */
//		public void onSelectedChanged(LTalentTree tTree, int selectedId);
//	}
//
//	
//	private class RankChangeListener implements 
//	LUpgrade.OnRankChangedListener{
//
//		public void onRankChanged(LUpgrade<?> upgrade, int newRank) {
//			updateUpgradabilityStates();
//		}
//
//	}
//
//}
