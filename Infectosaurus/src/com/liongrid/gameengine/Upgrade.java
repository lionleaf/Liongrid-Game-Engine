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
	
	protected int mRank = 0;
	protected int mMaxRank;
	
	public Upgrade(int maxRank){
		this.mMaxRank = maxRank;
	}
	
	/**
	 * @return the current mRank of the upgrade
	 */
	public int getRank(){
		return mRank;
	}
	
	/**
	 * Set`s the mRank. BEWARE, if mRank is out of bounds, it does nothing.
	 * @param mRank - the new mRank
	 * @return - True if the mRank was set, 
	 * 		false if mRank was greater than mMaxRank or less than zero
	 */
	public boolean setRank(int rank){
		if(rank > mMaxRank || rank < 0){
			return false;
		}
		
		this.mRank = rank;
		return true;
	}
	
	/**
	 * Increases the current mRank by 1, unless current mRank == max mRank
	 * @return true if the mRank was changed
	 */
	public boolean incrementRank(){
		if(mRank == mMaxRank) return false;
		
		mRank++;
		return true;
	}
	
	/**
	 * Decreases the current mRank by 1, unless current mRank = 0
	 * @return true if the mRank was changed
	 */
	public boolean decrementRank(){
		if(mRank == 0) return false;
		
		mRank--;
		return true;
	}
	
	/**
	 * Sets the mRank back to 0 and thereby disables the upgrade
	 */
	public void resetRank(){
		mRank = 0;
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
	 * @return the cost to upgrade this upgrade to the next mRank.
	 */
	public abstract int getUpgradePrice();

	public int getMaxRank() {
		return mMaxRank;
	}

	public abstract int getDescriptionRes();
	
}
