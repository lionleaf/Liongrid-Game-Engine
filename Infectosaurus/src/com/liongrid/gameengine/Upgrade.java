package com.liongrid.gameengine;

/**
 * @author Liongrid
 *	An abstract class for upgrades. 
 *	They should be applied before spawning upgradeable objects
 *	
 * It`s probably not necessary to pool this, 
 * and therefore it does not support it atm.
 * 
 * If the mRank is 0, the upgrade should do nothing!
 * 
 * To make it "infinitely" upgradeable, set mRank = Integer.MAX_VALUE;
 *  
 * @param <T> The type that the upgrade can be applied to
 */
public abstract class Upgrade<T extends BaseObject>{
	
	public static interface OnRankChangedListener{
		/**
		 * Called when the rank changes.
		 * @param upgrade - Pointer to the upgrade where the rank changed
		 * @param newRank - the new rank
		 */
		public void onRankChanged(Upgrade<?> upgrade, int newRank);
	}
	
	
	
	private OnRankChangedListener mOnRankChangedListener;
	protected int mRank = 0;
	
	protected int mMaxRank;
	
	public Upgrade(int maxRank){
		this.mMaxRank = maxRank;
	}
	
	/**
	 * Should be called upon spawning the object 
	 * that the upgrade should be applied to.
	 * 
	 * (You can change hp, dmg, components etc etc)
	 * 
	 * @param target - Pointer to the target of the upgrade
	 */
	public abstract void apply(T target);
	
	
	
	/**
	 * Decreases the current rank by 1, unless current rank == 0
	 * @return true if the rank was changed
	 */
	public boolean decrementRank(){
		return setRank(mRank-1);
	}
	
	public abstract int getDescriptionRes();
	
	public abstract String getCurrentStateDescription();
	
	public int getMaxRank() {
		return mMaxRank;
	}
	
	/**
	 * @return the current rank of the upgrade
	 */
	public int getRank(){
		return mRank;
	}
	
	
	/**
	 * @return the cost to upgrade this upgrade to the next rank.
	 */
	public abstract int getUpgradePrice();

	/**
	 * Increases the current rank by 1, unless current rank == max rank
	 * @return true if the rank was changed
	 */
	public boolean incrementRank(){
		return setRank(mRank+1);
	}

	/**
	 * Sets the rank back to 0 and thereby disables the upgrade
	 */
	public void resetRank(){
		setRank(0);
	}
	
	/**
	 * Set`s the mRank. BEWARE, if rank is out of bounds, it does nothing.
	 * This will notify any OnRankChangedListeners. 
	 * DO NOT CHANGE THE RANK ELSEWHERE
	 * 
	 * @param mRank - the new rank
	 * @return - True if the rank was set, 
	 * 		false if rank was greater than MaxRank or less than zero
	 */
	private boolean setRank(int rank){
		if(rank > mMaxRank || rank < 0){
			return false;
		}
		
		if(mRank != rank){
			this.mRank = rank;
			
			if(mOnRankChangedListener != null){
				mOnRankChangedListener.onRankChanged(this, rank);
			}
		}
		return true;
	}
	
	public void setOnRankChangedListener(OnRankChangedListener listener){
		mOnRankChangedListener = listener;
	}
	
}
